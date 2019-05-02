package com.yoga.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WalletData {

    @SerializedName("wallet_coins")
    private int walletCoins;
    @SerializedName("wallet_history")
    private ArrayList<WalletHistoryItem> walletHistory = null;

    @SerializedName("wallet_coins")
    public int getWalletCoins() {
        return walletCoins;
    }

    @SerializedName("wallet_coins")
    public void setWalletCoins(int walletCoins) {
        this.walletCoins = walletCoins;
    }

    @SerializedName("wallet_history")
    public ArrayList<WalletHistoryItem> getWalletHistory() {
        return walletHistory;
    }

    @SerializedName("wallet_history")
    public void setWalletHistory(ArrayList<WalletHistoryItem> walletHistory) {
        this.walletHistory = walletHistory;
    }

}
