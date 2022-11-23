package com.example.friendmanagement.api.response;

import java.util.List;

public class GetAllFriendsResponse {
    private boolean success;
    private List<String> friends;
    private int count;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
