package com.example.demo;


public class UserInfo extends Portfolio {
    public double accountBalance; //done
    public double plPoints;//done
    public double cumulativePLPoints;//done
    public double StartingBalance; //done
    public double cumulativeDifference; //done
    public double EndOfDayBalance; //done
    public boolean qualifiedForNextDay;//done
    public double differenceforday; //done
    public boolean isFirstTimeLogin; //done
    public double totalselltrade;
    public double successfultrade;
    public double winrate; // where win rate = successfultrade/totalselltrade * 100   
    private static final long serialVersionUID = 7744704048273285994L;

    public UserInfo() {
                this.plPoints = 0;
                this.cumulativePLPoints = 0;
                this.StartingBalance = 0;
                this.cumulativeDifference = 0;
                this.EndOfDayBalance = 0;
                this.qualifiedForNextDay = true;
                this.differenceforday = 0;
                this.totalselltrade=0;
                this.successfultrade=0;
                this.successfultrade=0;
                setStartingBalance();
        }
    public double getWinrate() {
            return winrate;
        }

    public void setWinrate(double winrate) {
            this.winrate = winrate;
    }
    
    public double getSuccessfultrade() {
        return successfultrade;
    }

    public void setSuccessfultrade(double successfultrade) {
        this.successfultrade = successfultrade;
    }
    public double getTotalselltrade() {
            return totalselltrade;
        }

    public void setTotalselltrade(double totalselltrade) {
            this.totalselltrade = totalselltrade;
    }
    public boolean isFirstTimeLogin(){
        return this.isFirstTimeLogin;
    }

    public void setFirstTimeLogin(boolean a){
        this.isFirstTimeLogin=a;
    }
    
    public double getcumulativePLPoints(){
        return cumulativePLPoints;
    }
    public void setcumulativePLPoints(double desired){
        this.cumulativePLPoints=desired;
    }   
    public void resetcumulativePLPoints(){
        this.cumulativePLPoints=0;
    }
    public double getcumulativeDifference(){
        return cumulativeDifference;
    }
    public void setcumulativeDifference(double desired){
        this.cumulativeDifference=desired;
    }
    public void resetcumulativedifference(){
        this.cumulativeDifference=0;
    }

    
    
    //preview oni
    public double previewPL(double buy,double sell){
        return sell-buy;
    }
    //find the P&L(difference) for each trade (call everytime for every trade)
    public double finddifferenceforeachtrade(double buy,double sell){
        //add into difference for day
        cumulativeDifference+=(sell-buy);
        differenceforday+=(sell-buy);
        totalselltrade++;
        if((sell-buy)>=0){
            successfultrade++;
        }
        winrate=successfultrade/totalselltrade;
        return sell-buy;
    }
    
    //return realtime balance
    public double getAccountBalance() {
        return accountBalance;
    }
    //each time trade, call this to update their account balance based on the difference;
    // can use finddifferenceforeachtrade
    public void updateAccountBalance(double finddifferenceforeachtrade){
        this.accountBalance+=finddifferenceforeachtrade;

    }

    public void setAccountBalance(double desired){
        this.accountBalance=desired;

    }
    //return realtime P&LPoints
    public double getPlPointsofday() {
        return plPoints;
    }
    public void setPlPointsofday(double desired){
        plPoints=desired;
    }
    //remember to set to 0 after each day start(only once)
    public void resetPlPointsforDay(){
        plPoints=0;
    }
    //find the P&L points for each trade (call everytime for every trade)
    //will automatically count for day and cumulative
    // return the current transaction P&L points
    public double calculatePLPoints(double a) {
        plPoints+=a;
        cumulativePLPoints+=a;
    // ----------> update PLpoints & cumulativePLpoints <-------------
        return a;
    }
    //return starting balance
    public double getStartingBalance() {
        return StartingBalance;
    }

    //call this method for the start of the day (call once oni) will not allow many times call
    public void setStartingBalance() {
        StartingBalance = accountBalance;
    }
    //use oni if needed
    public void resetStartingBalance(){
        StartingBalance=0;
    }
    //return differenceforday
    public double getDifferenceForDay() {
        return differenceforday;
    }
    //remember to set to 0 after each day
    public void resetDifferenceForDay(){
        differenceforday=0;
    }
    // Use oni when needed
    public void setDifferenceForDay(double desired) {
        differenceforday=desired;
    }

    //get endofdayBalance, end of day balance at the end of day
    public double getEndOfDayBalance() {
        return EndOfDayBalance;
    }
/// set the end of day balance at the end of day only
// remember to reset the counter everytime
    public void setEndOfDayBalance(double accountBalance) {
            EndOfDayBalance = accountBalance;
    }

    // Method to set qualification for the next day after end of day
    public boolean setQualificationForNextDay() {
        if ((accountBalance / getStartingBalance()) >= 0.5 || accountBalance<0) {
            qualifiedForNextDay=false;
            return qualifiedForNextDay;

        } else {
            qualifiedForNextDay=true;
            return qualifiedForNextDay;
        }
    }
    public boolean checkQualificationForNextDay() {
        if ((accountBalance / getStartingBalance()) >= 0.5 || accountBalance<0) {
            return false;

        } else {
            return true;
        }
    }
    //for admin
    public void setQualifiedForNextDay(boolean qualifiedForNextDay) {
        this.qualifiedForNextDay = qualifiedForNextDay;
    }
}

