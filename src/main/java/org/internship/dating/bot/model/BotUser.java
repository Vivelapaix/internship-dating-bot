package org.internship.dating.bot.model;

public class BotUser {
    private final long id;
    private final String name;
    private final UserType userType;
    private final UserState userState;

    public BotUser(long id, String name, UserType userType, UserState userState) {
        this.id = id;
        this.name = name;
        this.userType = userType;
        this.userState = userState;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserType getUserType() {
        return userType;
    }

    public UserState getUserState() {
        return userState;
    }

    public static class Builder {
        private long id;
        private String name;
        private UserType userType;
        private UserState userState;

        public Builder() {
        }

        public static BotUser.Builder user() {
            return new BotUser.Builder();
        }

        public BotUser.Builder id(long id) {
            this.id = id;
            return this;
        }

        public BotUser.Builder name(String name) {
            this.name = name;
            return this;
        }

        public BotUser.Builder userType(UserType userType) {
            this.userType = userType;
            return this;
        }

        public BotUser.Builder userState(UserState userState) {
            this.userState = userState;
            return this;
        }

        public BotUser build() {
            return new BotUser(
                id,
                name,
                userType,
                userState
            );
        }
    }
}
