import static Industry.*

version '2.1'
scoring '2.0649'

rule {
    multiplier '-1.0801'
    x 'creditAmountToPay'
    value {
        when { missing(x) } then '-1.357703228'
        when { x < 14410 } then '-1.357703228'
        when { x in 14410..<39606.5 } then '-0.396140725'
        when { x in 39606.5..<45850 } then '0.30921459'
        when { x >= 45850 } then '0.951932423'
    }
}

rule {
    multiplier '-0.9366'
    x 'borrowerIndustryId'
    value {
        when { missing(x) } then '0.50416979'
        when { [Industry.ID_ARMY, Industry.ID_ART, Industry.ID_MEDECINE, Industry.ID_SECURITY].contains(x) } then '0.50416979'
        when {
            [Industry.ID_GOV_SERVICE, Industry.ID_IT, Industry.ID_OTHER, Industry.ID_SALES, Industry.ID_SCIENCE, Industry.ID_SERVICE].contains(x)
        } then '0.105224359'
        when {
            [Industry.ID_BOOKKEEPING, Industry.ID_BUILDING, Industry.ID_FEC, Industry.ID_PRODUCTION, Industry.ID_RAWMATERIALSMINING, Industry.ID_TRANSPORT].contains(x)
        } then '-0.576053246'
        otherwise '0.50416979'
    }
}

rule {
    multiplier '-0.7127'
    x 'fcbSummaryHistoricalWorstPaymentStatusByActiveCreditsDays'
    value {
        when { missing(x) } then '0.557046775'
        when { x < 27 } then '-1.316805335'
        when { x >= 27 } then '0.289317396'
    }
}

rule {
    multiplier '-0.5987'
    x 'fcbSummaryMonthlyInstalmentsOwner'
    value {
        when { missing(x) } then '0.336132631'
        when { x < 29302.93 } then '0.336132631'
        when { x in 29302.93..<53000 } then '-0.627824038'
        when { x in 53000..<119566.1 } then '-1.405712447'
        when { x >= 119566.1 } then '-2.902821174'
    }
}

rule {
    multiplier '-1.1975'
    x 'realAddressRegionRes'
    value {
        when { missing(x) } then '0.297601725'
        when {
            ['akmolinckaia', 'aktubinskaia', 'almatinskaia', 'gamdilskaia', 'karagandinskaia', 'kizilordinskaia',
             'kostanaiskaia', 'pavlodarskaia', 'severokaz', 'ugnokaz', 'vostochnokaz', 'zapadnokaz'].contains(x)
        } then '0.297601725'
        when {
            ['almati', 'astana', 'atirauskaia', 'mangistauskaia'].contains(x)
        } then '-0.445048864'
        otherwise '0.297601725'
    }
}

rule {
    multiplier '-0.9415'
    x 'sociohubSumGroupsN'
    value {
        when { missing(x) } then '0.137923956'
        when { x < 31 } then '-0.360914311'
        when { x >= 31 } then '0.379489622'
    }
}

result { scoring ->
    Math.round((1.0 / (1.0 + Math.exp(-1 * scoring)) * 1000))
}
