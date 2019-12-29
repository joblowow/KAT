package com.sample;

import com.sample.Fact;

public class Business {
	public int hobSemiPro;
	public int isUBNRegistered;
	public int isKvKRegistered;
	public int moneyToSpend; // We still need a total calculation for if money to spend is smaller than money needed
	public int timeWillingToSpend;
	//public int slaugBreedBoth
	//public int moneyNeeded;
	//public int hasEnoughMoney;
	public String advice = "<html>";
	
	public Business() {
		
	}

	public int getHobSemiPro() {
		return hobSemiPro;
	}

	public void setHobSemiPro(Fact hobSemiPro) {
		this.hobSemiPro = hobSemiPro.getAnswer();
	}

	public int getIsUBNRegistered() {
		return isUBNRegistered;
	}

	public void setIsUBNRegistered(Fact isUBNRegistered) {
		this.isUBNRegistered = isUBNRegistered.getAnswer();
	}

	public int getIsKvKRegistered() {
		return isKvKRegistered;
	}

	public void setIsKvKRegistered(Fact isKvKRegistered) {
		this.isKvKRegistered = isKvKRegistered.getAnswer();
	}

	public int getMoneyToSpend() {
		return moneyToSpend;
	}

	public void setMoneyToSpend(Fact moneyToSpend) {
		this.moneyToSpend = moneyToSpend.getAnswer();
	}

	public int getTimeWillingToSpend() {
		return timeWillingToSpend;
	}

	public void setTimeWillingToSpend(Fact timeWillingToSpend) {
		this.timeWillingToSpend = timeWillingToSpend.getAnswer();
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		if(this.advice.equals("<html>")) {
			this.advice = this.advice + advice;
		} else {
			this.advice = this.advice + "<br>" +  advice;
		}
	}
	
	
	
}
