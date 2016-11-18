package com.satti.memorygame.network;

import com.satti.memorygame.network.model.Item;

import java.util.List;

/**
 * Created by satish on 18/11/16.
 */

public interface RetrofitOnDownloadListener {

    void onDownloadComplete(List<Item> flickritems);

}
