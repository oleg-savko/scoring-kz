public class ScoringParams {

    private Long id;
    private Double creditAmountToPay;
    private Integer creditCountDays;
    private Double creditInitialAmount;

    private Integer fcbSummaryHistoricalWorstPaymentStatusByActiveCreditsDays;
    private Double fcbSummaryMonthlyInstalmentsOwner;
    private Integer sociohubSumGroupsN;
    private String realAddressRegionRes;

    private Long borrowerIndustryId;

    private Long borrowerPostId;
    private Sex borrowerSex;
    private Double borrowerPaymentsLoan;
    private Education borrowerEducation;

    private Integer lastCreditCompletedDays;
    private Integer lastCreditOpenDays;
    private Integer expiredTimes1dayLast6month;
    private Double fcbAppCapsTotalAmountL12m;

    private Integer fcbGcvpEmployersCount3Months;

    private Double fcbSummaryHistoricalWorstPaymentStatusByActiveCreditsSum;
    private Double historyPercentToPay;
    private Integer qiwiTotalMaxPayment;

    private Long expiredCountDaysAllMonth;
    private Integer fcbSummaryCreditsAllOwner;

    private Integer qiwiAverageSum;
    private Integer qiwiMaxDifferenceInDays;

    public Long getId() {
        return id;
    }

    public Integer getQiwiMaxDifferenceInDays() {
        return qiwiMaxDifferenceInDays;
    }

    public void setQiwiMaxDifferenceInDays(Integer qiwiMaxDifferenceInDays) {
        this.qiwiMaxDifferenceInDays = qiwiMaxDifferenceInDays;
    }

    public Integer getQiwiAverageSum() {
        return qiwiAverageSum;
    }

    public void setQiwiAverageSum(Integer qiwiAverageSum) {
        this.qiwiAverageSum = qiwiAverageSum;
    }

    public Integer getFcbSummaryCreditsAllOwner() {
        return fcbSummaryCreditsAllOwner;
    }

    public void setFcbSummaryCreditsAllOwner(Integer fcbSummaryCreditsAllOwner) {
        this.fcbSummaryCreditsAllOwner = fcbSummaryCreditsAllOwner;
    }

    public Long getExpiredCountDaysAllMonth() {
        return expiredCountDaysAllMonth;
    }

    public void setExpiredCountDaysAllMonth(Long expiredCountDaysAllMonth) {
        this.expiredCountDaysAllMonth = expiredCountDaysAllMonth;
    }

    public Integer getQiwiTotalMaxPayment() {
        return qiwiTotalMaxPayment;
    }

    public void setQiwiTotalMaxPayment(Integer qiwiTotalMaxPayment) {
        this.qiwiTotalMaxPayment = qiwiTotalMaxPayment;
    }

    public Double getHistoryPercentToPay() {
        return historyPercentToPay;
    }

    public void setHistoryPercentToPay(Double historyPercentToPay) {
        this.historyPercentToPay = historyPercentToPay;
    }

    public Double getFcbSummaryHistoricalWorstPaymentStatusByActiveCreditsSum() {
        return fcbSummaryHistoricalWorstPaymentStatusByActiveCreditsSum;
    }

    public void setFcbSummaryHistoricalWorstPaymentStatusByActiveCreditsSum(
            Double fcbHistoricalWorstPaymentStatusByActiveCreditsSum) {
        this.fcbSummaryHistoricalWorstPaymentStatusByActiveCreditsSum =
                fcbHistoricalWorstPaymentStatusByActiveCreditsSum;
    }

    public Double getFcbAppCapsTotalAmountL12m() {
        return fcbAppCapsTotalAmountL12m;
    }

    public void setFcbAppCapsTotalAmountL12m(Double fcbAppCapsTotalAmountL12m) {
        this.fcbAppCapsTotalAmountL12m = fcbAppCapsTotalAmountL12m;
    }

    public Integer getExpiredTimes1dayLast6month() {
        return expiredTimes1dayLast6month;
    }

    public void setExpiredTimes1dayLast6month(Integer expiredTimes1dayLast6month) {
        this.expiredTimes1dayLast6month = expiredTimes1dayLast6month;
    }

    public Integer getLastCreditOpenDays() {
        return lastCreditOpenDays;
    }

    public void setLastCreditOpenDays(Integer creditLastOpenDays) {
        this.lastCreditOpenDays = creditLastOpenDays;
    }

    public Integer getLastCreditCompletedDays() {
        return lastCreditCompletedDays;
    }

    public void setLastCreditCompletedDays(Integer creditLastCompletedDays) {
        this.lastCreditCompletedDays = creditLastCompletedDays;
    }

    public Education getBorrowerEducation() {
        return borrowerEducation;
    }

    public void setBorrowerEducation(Education borrowerEducation) {
        this.borrowerEducation = borrowerEducation;
    }

    public Double getBorrowerPaymentsLoan() {
        return borrowerPaymentsLoan;
    }

    public void setBorrowerPaymentsLoan(Double borrowerPaymentsLoan) {
        this.borrowerPaymentsLoan = borrowerPaymentsLoan;
    }

    public Sex getBorrowerSex() {
        return borrowerSex;
    }

    public void setBorrowerSex(Sex borrowerDataSex) {
        this.borrowerSex = borrowerDataSex;
    }

    public Long getBorrowerPostId() {
        return borrowerPostId;
    }

    public void setBorrowerPostId(Long borrowerDataPost) {
        this.borrowerPostId = borrowerDataPost;
    }

    public Long getBorrowerIndustryId() {
        return borrowerIndustryId;
    }

    public void setBorrowerIndustryId(Long borrowerIndustryId) {
        this.borrowerIndustryId = borrowerIndustryId;
    }

    public String getRealAddressRegionRes() {
        return realAddressRegionRes;
    }

    public void setRealAddressRegionRes(String realAddressRegionRes) {
        this.realAddressRegionRes = realAddressRegionRes;
    }

    public Integer getSociohubSumGroupsN() {
        return sociohubSumGroupsN;
    }

    public void setSociohubSumGroupsN(Integer sociohubSumGroupsN) {
        this.sociohubSumGroupsN = sociohubSumGroupsN;
    }

    public Double getFcbSummaryMonthlyInstalmentsOwner() {
        return fcbSummaryMonthlyInstalmentsOwner;
    }

    public void setFcbSummaryMonthlyInstalmentsOwner(Double fcbSummaryMonthlyInstalmentsOwner) {
        this.fcbSummaryMonthlyInstalmentsOwner = fcbSummaryMonthlyInstalmentsOwner;
    }

    public Integer getFcbSummaryHistoricalWorstPaymentStatusByActiveCreditsDays() {
        return fcbSummaryHistoricalWorstPaymentStatusByActiveCreditsDays;
    }

    public void setFcbSummaryHistoricalWorstPaymentStatusByActiveCreditsDays(
            Integer fcbSummaryHistoricalWorstPaymentStatusByActiveCreditsDays) {
        this.fcbSummaryHistoricalWorstPaymentStatusByActiveCreditsDays =
                fcbSummaryHistoricalWorstPaymentStatusByActiveCreditsDays;
    }

    public Double getCreditInitialAmount() {
        return creditInitialAmount;
    }

    public void setCreditInitialAmount(Double creditInitialAmount) {
        this.creditInitialAmount = creditInitialAmount;
    }

    public Integer getCreditCountDays() {
        return creditCountDays;
    }

    public void setCreditCountDays(Integer creditCountDays) {
        this.creditCountDays = creditCountDays;
    }

    public Double getCreditAmountToPay() {
        return creditAmountToPay;
    }

    public void setCreditAmountToPay(Double creditAmountToPay) {
        this.creditAmountToPay = creditAmountToPay;
    }

    public Integer getFcbGcvpEmployersCount3Months() {
        return fcbGcvpEmployersCount3Months;
    }

    public void setFcbGcvpEmployersCount3Months(Integer fcbGcvpEmployersCount3Months) {
        this.fcbGcvpEmployersCount3Months = fcbGcvpEmployersCount3Months;
    }
}
