package DomainLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Pure Fabrication  // extra class for avoiding the user to like or dislike the video twice.
 * When program starts, user can not like or dislike the same video twice.
 * User's information that associatiated the videos, will be stored this class for loose coupling.
 * 
 */

public class UserInformation { 

    private List<Video> watchList;
    private List<Video> likesVideoList;
    private List<Video> dislikesVideoList;

    public UserInformation() {
        watchList =new ArrayList<>();
        likesVideoList =new ArrayList<>();
        dislikesVideoList =new ArrayList<>();

    }


    public boolean hasVideoWatchList(String videoTitle) {
        for (Video video:watchList) {
            if (video.getTitle().equals(videoTitle)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasVideoLikedList(String videoTitle) {
        for (Video video:likesVideoList) {
            if (video.getTitle().equals(videoTitle)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasVideoDisLikedList(String videoTitle) {
        for (Video video:dislikesVideoList) {
            if (video.getTitle().equals(videoTitle)) {
                return true;
            }
        }
        return false;
    }

    public List<Video> getDislikesVideoList() {
        return dislikesVideoList;
    }

    public List<Video> getLikesVideoList() {
        return likesVideoList;
    }

    public List<Video> getWatchList() {
        return watchList;
    }

    public void addVideoToWatchlist(Video v) {
                    watchList.add(v);
                }

                public void removeVideoFromWatchList(Video v) {
                    watchList.remove(v);
                }

                public void  addVideoToLikesList(Video v) {
                    if (dislikesVideoList.contains(v)) {
                        removeFromDislikesList(v);
                    }
                    likesVideoList.add(v);
                }
    public void  removeFromLikesList(Video v) {
        likesVideoList.remove(v);
    }

    public void  addVideoToDisLikesList(Video v) {
        if (likesVideoList.contains(v)) {
            removeFromLikesList(v);
        }
        dislikesVideoList.add(v);
    }
    public void  removeFromDislikesList(Video v) {
        dislikesVideoList.remove(v);
    }





}
