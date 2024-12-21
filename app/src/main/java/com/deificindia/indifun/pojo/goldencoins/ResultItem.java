package com.deificindia.indifun.pojo.goldencoins;

import com.google.gson.annotations.SerializedName;

public class ResultItem {

    @SerializedName("coin_amount")
    private int coinAmount;

    @SerializedName("entrydate")
    private String entrydate;

    @SerializedName("coin_type")
    private String coinType;

    @SerializedName("id")
    private int id;

    @SerializedName("coin_point")
    private int coin_point;

    @SerializedName("golden_coins")
    private int golden_coins;

    public int getGolden_coins() {
        return golden_coins;
    }

    public void setGolden_coins(int golden_coins) {
        this.golden_coins = golden_coins;
    }

    public void setCoinAmount(int coinAmount) {
        this.coinAmount = coinAmount;
    }

    public int getCoinAmount() {
        return coinAmount;
    }

    public void setEntrydate(String entrydate) {
        this.entrydate = entrydate;
    }

    public String getEntrydate() {
        return entrydate;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

	public int getCoin_point() {
		return coin_point;
	}

	public void setCoin_point(int coin_point) {
		this.coin_point = coin_point;
	}

	@Override
    public String toString() {
        return
                "ResultItem{" +
                        "coin_amount = '" + coinAmount + '\'' +
                        ",entrydate = '" + entrydate + '\'' +
                        ",coin_type = '" + coinType + '\'' +
                        ",id = '" + id + '\'' +
                        ",coin_point = '" + coin_point + '\'' +
                        "}";
    }
}