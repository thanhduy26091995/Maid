package com.hbbsolution.maid.more.viet_pham;


import com.hbbsolution.maid.model.announcement.AnnouncementResponse;

/**
 * Created by buivu on 29/06/2017.
 */

public interface MoreForAnnouncementView {
    void onAnnouncement(AnnouncementResponse announcementResponse);

    void offAnnouncement(AnnouncementResponse announcementResponse);

    void getError(String error);
}
