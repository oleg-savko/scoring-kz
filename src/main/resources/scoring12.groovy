
import static Post.*
import static Sex.*

version '1.2'
scoring '2.8404'

rule {
    multiplier '-0.6134'
    x 'creditCountDays'
    value {
        when { missing(x) } then '-1.321143408'
        when { x < 14 } then '-1.321143408'
        when { x in 14..<31 } then '0.002823997'
        when { x >= 31 } then '0.808535128'
    }
}

rule {
    multiplier '-0.9159'
    x 'borrowerPaymentsLoan'
    value {
        when { missing(x) } then '0.316058643'
        when { x < 27000 } then '0.316058643'
        when { x in 27000..<39000 } then '-0.764201418'
        when { x >= 39000 } then '-2.522703666'
    }
}

rule {
    multiplier '-0.9296'
    x 'borrowerPostId'
    value {
        when { missing(x) } then '0.477577072'
        when { [Post.CHIEF_ID, Post.CHIEF_ACCOUNTANT_ID, Post.DIRECTOR_ID, Post.WORKER_ID].contains(x) } then '0.477577072'
        when { [Post.EMPLOYEE_ID].contains(x) } then '-0.393356511'
        otherwise '0.477577072'
    }
}

rule {
    multiplier '-1.0217'
    x 'borrowerSex'
    value {
        when { missing(x) } then '0.249122571'
        when { x == Sex.MAN } then '0.249122571'
        when { x == Sex.WOMAN } then '-0.33577784'
        otherwise '-0.33577784'
    }
}

rule {
    multiplier '-0.8098'
    x 'lastCreditCompletedDays'
    value {
        when { missing(x) } then '0.777720365'
        when { x < 1 } then '0.777720365'
        when { x in 1..<6 } then '-0.016640816'
        when { x >= 6 } then '-0.37370444'
    }
}

rule {
    multiplier '-0.9282'
    x 'lastCreditOpenDays'
    value {
        when { missing(x) } then '0.337084669'
        when { x < 14 } then '0.337084669'
        when { x in 14..<47 } then '-0.281027326'
        when { x >= 47 } then '0.354961441'
    }
}

rule {
    multiplier '-0.8641'
    x 'expiredTimes1dayLast6month'
    value {
        when { missing(x) } then '-0.353223617'
        when { x < 1 } then '-0.353223617'
        when { x >= 1 } then '0.858406958'
    }
}

rule {
    multiplier '-0.784'
    x 'fcbAppCapsTotalAmountL12m'
    value {
        when { missing(x) } then '0.534458707'
        when { x < 50000 } then '-0.35647788'
        when { x >= 50000 } then '0.534458707'
    }
}

rule {
    multiplier '-0.9'
    x 'fcbSummaryHistoricalWorstPaymentStatusByActiveCreditsSum'
    value {
        when { missing(x) } then '0.510464939'
        when { x < 6535 } then '-1.046193099'
        when { x >= 6535 } then '-0.013866889'
    }
}

rule {
    multiplier '-0.6851'
    x 'historyPercentToPay'
    value {
        when { missing(x) } then '-0.364699801'
        when { x < 4320 } then '-0.364699801'
        when { x in 4320..<11200 } then '0.279082656'
        when { x >= 11200 } then '0.675310907'
    }
}

rule {
    multiplier '-0.9524'
    x 'qiwiTotalMaxPayment'
    value {
        when { missing(x) } then '0.275236648'
        when { x < 2000 } then '0.275236648'
        when { x >= 2000 } then '-0.53402384'
    }
}

result { scoring ->
    Math.round((1.0 / (1.0 + Math.exp(-1 * scoring)) * 1000))
}