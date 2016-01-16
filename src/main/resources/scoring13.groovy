
import static Education.*

version '1.3'
scoring '3.7667'

rule {
    multiplier '-1.8602'
    x 'creditInitialAmount'
    value {
        when { missing(x) } then '-0.244082677'
        when { x < 72000 } then '-0.244082677'
        when { x >= 72000 } then '0.599747497'
    }
}

rule {
    multiplier '-0.9828'
    x 'borrowerEducation'
    value {
        when { missing(x) } then '0.445829579'
        when { [Education.HIGHER].contains(x) } then '-0.494378704'
        when { [Education.INCOMPLETE_HIGHER, Education.LOWER_SECONDARY, Education.OTHER, Education.SECONDARY].contains(x) } then '0.445829579'
        otherwise '0.445829579'
    }
}

rule {
    multiplier '-1.1093'
    x 'expiredCountDaysAllMonth'
    value {
        when { missing(x) } then '-0.694135323'
        when { x < 3 } then '-0.694135323'
        when { x in 3..<21 } then '0.768094964'
        when { x >= 21} then '2.116193727'
    }
}

rule {
    multiplier '-1.0197'
    x 'fcbSummaryCreditsAllOwner'
    value {
        when { missing(x) } then '0.504868259'
        when { x < 6 } then '0.504868259'
        when { x >= 6 } then '-0.813289161'
    }
}

rule {
    multiplier '-1.0646'
    x 'qiwiAverageSum'
    value {
        when { missing(x) } then '-0.259055294'
        when { x < 383 } then '0.853354567'
        when { x >= 383 } then '-0.259055294'
    }
}

rule {
    multiplier '-0.7873'
    x 'qiwiMaxDifferenceInDays'
    value {
        when { missing(x) } then '-0.576352287'
        when { x < 33 } then '0.649665801'
        when { x >= 33 } then '-0.576352287'
    }
}

result { scoring ->
    Math.round((1.0 / (1.0 + Math.exp(-1 * scoring)) * 1000))
}