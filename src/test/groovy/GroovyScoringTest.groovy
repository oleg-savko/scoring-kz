import com.google.common.base.CaseFormat
import groovy.sql.Sql
import org.codehaus.groovy.control.CompilerConfiguration
import scoring.ScoringCalculateClass
import spock.lang.Shared
import spock.lang.Specification

class GroovyScoringTest extends Specification {

    static final def DROP_TEMP_TABLE = "drop table if exists tmp_cc_borrower"
    static final def CREATE_TEMP_TABLE = "CREATE temporary TABLE tmp_cc_borrower (index (borrower_id))\n" +
            " engine memory\n" +
            " as SELECT max(status_days_count) as max, c.borrower_id as borrower_id, c.id as credit_id FROM credit_calculations cc\n" +
            " inner join credit c on c.id = cc.credit_id\n" +
            " where cc.status = 'EXPIRED'\n" +
            " group by c.borrower_id;"

    static final def SQL_QUERY = "SELECT\n" +
            "\t\t  null as id, \n" +
            "          c.amount_to_pay                              AS credit_amount_to_pay,\n" +
            "          c.credit_count_days                          AS credit_count_days,\n" +
            "          c.initial_amount                             AS credit_initial_amount,\n" +
            "          DATEDIFF(c.date_requested, (SELECT lcc.date_repaid\n" +
            "                                      FROM credit lcc\n" +
            "                                      WHERE lcc.borrower_id = borrower_id AND lcc.status = 'COMPLETED' AND lcc.id < c.id\n" +
            "                                      ORDER BY id DESC\n" +
            "                                      LIMIT 1))        AS last_credit_completed_days,\n" +
            "          DATEDIFF(c.date_requested, (SELECT lcc.date_requested\n" +
            "                                      FROM credit lcc\n" +
            "                                      WHERE lcc.borrower_id = borrower_id AND lcc.status = 'COMPLETED' AND lcc.id < c.id\n" +
            "                                      ORDER BY id DESC\n" +
            "                                      LIMIT 1))        AS last_credit_open_days,\n" +
            "  w.education as borrower_education,\n" +
            "  w.post_id as borrower_post_id,\n" +
            "  p.sex as borrower_sex,\n" +
            "  p.payments_loans as borrower_payments_loan,\n" +
            "  w.industry_id as borrower_industry_id,\n" +
            "          substring(r.resource FROM 10)                AS real_address_region_res,\n" +
            "  qsi.maxDifferenceInDays as qiwi_max_difference_in_days,\n" +
            "  qsi.averageSum as qiwi_average_sum,\n" +
            "  qsi.totalMaxPayment as qiwi_total_max_payment,\n" +
            "  fcb_sum.credits_all_owner as fcb_summary_credits_all_owner,\n" +
            "  fcb_sum.monthly_instalments_owner as fcb_summary_monthly_instalments_owner,\n" +
            "  fcb_sum.historical_worst_payment_status_by_active_credits_days as fcb_summary_historical_worst_payment_status_by_active_credits_days,\n" +
            "  fcb_sum.current_worst_payment_status_by_active_credits_sum as fcb_summary_historical_worst_payment_status_by_active_credits_sum,\n" +
            "  fcas.capsTotalAmountL12m as fcb_app_caps_total_amount_l_12m,\n" +
            "  sad.sum_groups_n as sociohub_sum_groups_n,\n" +
            "  tcb.max as expired_count_days_all_month,\n" +
            "  null as fcb_gcvp_employers_count_3_months,\n" +
            "\t(SELECT count(cc.id)\n" +
            "           FROM credit_calculations cc, credit c1\n" +
            "           WHERE cc.credit_id = c1.id AND c1.borrower_id = b.id AND c1.id < c.id AND\n" +
            "                 ((cc.status = 'EXPIRED' AND cc.status_days_count = 1)\n" +
            "                  OR (cc.status = 'COMPLETED' AND c1.penalty_added_date = c1.date_repaid))\n" +
            "                 AND c1.penalty_added_date >= date_sub(date(c.date_requested), INTERVAL 6 MONTH) AND\n" +
            "                 c1.date_requested < c.date_requested) AS expired_times_1day_last_6month,\n" +
            "\n" +
            "\t(SELECT percent\n" +
            "           FROM credit_payment cp, credit c1\n" +
            "           WHERE cp.credit_id = c1.id AND c1.id < c.id AND c1.borrower_id = b.id AND cp.payment_source != 'BONUS'\n" +
            "           ORDER BY payment_date DESC\n" +
            "           LIMIT 1)                                    AS history_percent_to_pay,\n" +
            "  crf.scoring_result11 as scoring_result1_1,\n" +
            "  crf.scoring_result21 as scoring_result2_1,\n" +
            "  crf.scoring_result1_2  as scoring_result1_2,\n" +
            "  crf.scoring_result1_3 as scoring_result1_3\n" +
            "FROM credit c\n" +
            "  LEFT OUTER JOIN borrower b ON c.borrower_id = b.id\n" +
            "  LEFT OUTER JOIN WORK w ON b.work_id = w.id\n" +
            "  LEFT OUTER JOIN personal_data p on p.id = b.personal_data_id \n" +
            "  LEFT OUTER JOIN address a ON b.real_address_id = a.id\n" +
            "  LEFT OUTER JOIN region r ON a.region_id = r.id\n" +
            "  LEFT OUTER JOIN user_account ua ON b.user_account_id = ua.id\n" +
            "  LEFT OUTER JOIN qiwi_scoring qs ON qs.number = ua.phone AND c.borrower_id = qs.credit_id\n" +
            "  LEFT OUTER JOIN qiwi_scoring_info qsi ON qsi.qiwi_scoring_id = qs.id\n" +
            "  LEFT OUTER JOIN fcb_summary fcb_sum ON fcb_sum.credit_id = c.id\n" +
            "  LEFT OUTER JOIN fcb_credit_app_summary fcas ON c.id = fcas.credit_id\n" +
            "  LEFT OUTER JOIN sociohub_agregation_data sad ON sad.borrower_id = b.id\n" +
            "  left OUTER JOIN credit_risk_filter crf on crf.credit_id = c.id\n" +
            "  left outer join tmp_cc_borrower tcb on tcb.borrower_id = b.id and tcb.credit_id < c.id " +
            " where scoring_result1_2 is not null and scoring_result1_2 != 0 limit 10;"


    static final def JDBC_URL = "jdbc:mysql://127.0.0.1:3306/kz_master_moneyman"
    static final def JDBC_USER = "root"
    static final def JDBC_PASS = "s1o99A4j1An"

    @Shared
    static Sql sql
    @Shared
    static GroovyShell shell
    public static final Set SCORING_PARAMS_KEY_SET = new ScoringParams().getProperties().keySet()

    private String sqlLike(String field) {
        String converted = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field);
        StringBuilder b = new StringBuilder();
        boolean nowDigits = false;

        for (int i = 0; i < converted.toCharArray().length; i++) {
            char ch = converted.toCharArray()[i];
            if (Character.isDigit(ch)) {
                if (!nowDigits) {
                    b.append("_");
                    nowDigits = true;
                }
            } else {
                nowDigits = false;
            }
            b.append(ch);
        }

        return b.toString();
    }

    def setupSpec() {
        sql = Sql.newInstance(JDBC_URL, JDBC_USER, JDBC_PASS, 'com.mysql.jdbc.Driver')
        sql.execute(DROP_TEMP_TABLE)
        sql.execute(CREATE_TEMP_TABLE)

        def config = new CompilerConfiguration()
        config.scriptBaseClass = ScoringCalculateClass.class.name

        def binding = new Binding()
        shell = new GroovyShell(binding, config)
    }

    def "test"() {
        def rows = sql.rows(SQL_QUERY)
        rows.each { row ->
            def params = new ScoringParams();
            SCORING_PARAMS_KEY_SET.each { String propName ->
                switch ("$propName"){
                    case "borrowerSex":
                        params."${propName}" = Sex.values()[row[sqlLike(propName)]]
                        break
                    case "borrowerEducation":
                        params."${propName}" = Education.values()[row[sqlLike(propName)]]
                        break;
                    case "class":
                        // just skip
                        break
                    default:
                        params."${propName}" = row[sqlLike(propName)]
                }
            }
            shell.setVariable("scoringParams", params)

            def res = shell.evaluate(GroovyScoringTest.class.getResource("scoring11.groovy").toURI())
            println shell.getVariable("scoring")
            println shell.getVariable("result")
            println row["scoring_result1_1"]

            res = shell.evaluate(GroovyScoringTest.class.getResource("scoring12.groovy").toURI())
            println shell.getVariable("scoring")
            println shell.getVariable("result")
            println row["scoring_result1_2"]

            res = shell.evaluate(GroovyScoringTest.class.getResource("scoring13.groovy").toURI())
            println shell.getVariable("scoring")
            println shell.getVariable("result")
            println row["scoring_result1_3"]

            res = shell.evaluate(GroovyScoringTest.class.getResource("scoring21.groovy").toURI())
            println shell.getVariable("scoring")
            println shell.getVariable("result")
            println row["scoring_result2_1"]
        }

        expect:
        1 == 1

    }

}