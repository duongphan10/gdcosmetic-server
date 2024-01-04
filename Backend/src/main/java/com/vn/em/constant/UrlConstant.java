package com.vn.em.constant;

public class UrlConstant {

    public static class Auth {
        private static final String PRE_FIX = "/auth";
        public static final String LOGIN = PRE_FIX + "/login";
        public static final String LOGOUT = PRE_FIX + "/logout";
        public static final String SEND_VERIFY = PRE_FIX + "/send";
        public static final String VERIFY = PRE_FIX + "/verify";

        private Auth() {
        }
    }

    public static class User {
        private static final String PRE_FIX = "/user";
        public static final String GET_USER = PRE_FIX + "/{id}";
        public static final String GET_CURRENT_USER = PRE_FIX + "/current";
        public static final String GET_ALL = PRE_FIX + "/all";
        public static final String SEARCH= PRE_FIX + "/search";
        public static final String CREATE = PRE_FIX + "/create";
        public static final String UPDATE = PRE_FIX + "/{id}";
        public static final String CHANGE_AVATAR = PRE_FIX + "/change/avatar";
        public static final String CHANGE_PASSWORD = PRE_FIX + "/change/password";
        public static final String CREATE_NEW_PASSWORD = PRE_FIX + "/create/new-password";
        public static final String DELETE = PRE_FIX + "/{id}";

        private User() {
        }
    }

    public static class Department {
        public static final String PRE_FIX = "/department";
        public static final String GET_BY_ID = PRE_FIX + "/{id}";
        public static final String GET_ALL = PRE_FIX + "/all";
        public static final String CREATE = PRE_FIX + "/create";
        public static final String UPDATE = PRE_FIX + "/{id}";
        public static final String DELETE = PRE_FIX + "/{id}";

        private Department() {
        }
    }

    public static class Position {
        public static final String PRE_FIX = "/position";
        public static final String GET_BY_ID = PRE_FIX + "/{id}";
        public static final String GET_ALL = PRE_FIX + "/all";
        public static final String CREATE = PRE_FIX + "/create";
        public static final String UPDATE = PRE_FIX + "/{id}";
        public static final String DELETE = PRE_FIX + "/{id}";

        private Position() {
        }
    }

    public static class Employee {
        public static final String PRE_FIX = "/employee";
        public static final String GET_BY_ID = PRE_FIX + "/{id}";
        public static final String GET_BY_CODE = PRE_FIX + "/get";
        public static final String GET_ALL = PRE_FIX + "/all";
        public static final String GET_ALL_BY_ROLE = PRE_FIX;
        public static final String SEARCH = PRE_FIX + "/search";
        public static final String CREATE = PRE_FIX + "/create";
        public static final String UPDATE = PRE_FIX + "/{id}";
        public static final String DELETE = PRE_FIX + "/{id}";

        private Employee() {
        }
    }

    public static class SalaryAdjustment {
        public static final String PRE_FIX = "/salary-adjustment";
        public static final String GET_BY_ID = PRE_FIX + "/{id}";
        public static final String GET_ALL = PRE_FIX + "/all";
        public static final String SEARCH = PRE_FIX + "/search";
        public static final String GET_MY_CREATE = PRE_FIX + "/my/all";
        public static final String SEARCH_MY_CREATE = PRE_FIX + "/my/search";
        public static final String CREATE = PRE_FIX + "/create";
        public static final String UPDATE = PRE_FIX + "/{id}";
        public static final String DELETE = PRE_FIX + "/{id}";

        private SalaryAdjustment() {
        }
    }

    public static class Recognition {
        public static final String PRE_FIX = "/recognition";
        public static final String GET_BY_ID = PRE_FIX + "/{id}";
        public static final String GET_ALL = PRE_FIX + "/all";
        public static final String SEARCH = PRE_FIX + "/search";
        public static final String GET_MY_CREATE = PRE_FIX + "/my/all";
        public static final String SEARCH_MY_CREATE = PRE_FIX + "/my/search";
        public static final String CREATE = PRE_FIX + "/create";
        public static final String UPDATE = PRE_FIX + "/{id}";
        public static final String DELETE = PRE_FIX + "/{id}";

        private Recognition() {
        }
    }

    public static class Attendance {
        public static final String PRE_FIX = "/attendance";
        public static final String GET_BY_ID = PRE_FIX + "/{id}";
        public static final String GET_ALL = PRE_FIX + "/all";
        public static final String SEARCH = PRE_FIX + "/search";
        public static final String CREATE = PRE_FIX + "/create";
        public static final String UPDATE = PRE_FIX + "/{id}";
        public static final String DELETE = PRE_FIX + "/{id}";

        private Attendance() {
        }
    }

    public static class Salary {
        public static final String PRE_FIX = "/salary";
        public static final String GET_BY_ID = PRE_FIX + "/{id}";
        public static final String GET_ALL = PRE_FIX + "/all";
        public static final String SEARCH = PRE_FIX + "/search";
        public static final String CREATE = PRE_FIX + "/create";
        public static final String UPDATE = PRE_FIX + "/{id}";
        public static final String PAY_ALL = PRE_FIX + "/pay/all";
        public static final String PAY_BY_ID = PRE_FIX + "/pay/{id}";
        public static final String DELETE = PRE_FIX + "/{id}";

        private Salary() {
        }
    }

    public static class Project {
        public static final String PRE_FIX = "/project";
        public static final String GET_BY_ID = PRE_FIX + "/{id}";
        public static final String GET_ALL = PRE_FIX + "/all";
        public static final String SEARCH = PRE_FIX + "/search";
        public static final String GET_ALL_MY_PROJECT = PRE_FIX + "/my/all";
        public static final String SEARCH_MY_PROJECT = PRE_FIX + "/my/search";
        public static final String CREATE = PRE_FIX + "/create";
        public static final String UPDATE = PRE_FIX + "/{id}";
        public static final String DELETE = PRE_FIX + "/{id}";

        private Project() {
        }
    }

    public static class Task {
        public static final String PRE_FIX = "/task";
        public static final String GET_BY_ID = PRE_FIX + "/{id}";
        public static final String GET_ALL = PRE_FIX + "/all";
        public static final String GET_MY_TASK = PRE_FIX + "/my";
        public static final String SEARCH_MY_TASK = PRE_FIX + "/my/search";
        public static final String CREATE = PRE_FIX + "/create";
        public static final String UPDATE = PRE_FIX + "/{id}";
        public static final String DELETE = PRE_FIX + "/{id}";

        private Task() {
        }
    }

    public static class Room {
        public static final String PRE_FIX = "/room";
        public static final String GET_BY_ID = PRE_FIX + "/{id}";
        public static final String GET_ALL_MY_CONVERSATION = PRE_FIX + "/conversation/all";
        public static final String GET_ALL_USER = PRE_FIX + "/{id}/user/all";
        public static final String SEARCH_OTHER_USER = PRE_FIX + "/{id}/user/other";
        public static final String CREATE = PRE_FIX + "/create";
        public static final String ADD_USER = PRE_FIX + "/user/add";
        public static final String UPDATE = PRE_FIX + "/{id}";
        public static final String CHANGE_NICKNAME = PRE_FIX + "/user/change";
        public static final String DELETE = PRE_FIX + "/{id}";
        public static final String LEAVE = PRE_FIX + "/user/leave";

        private Room() {
        }
    }

    public static class Message {
        public static final String PRE_FIX = "/message";
        public static final String GET_ALL_BY_ROOM_ID = PRE_FIX + "/all/{roomId}";
        public static final String SEND = PRE_FIX + "/send";

        private Message() {
        }
    }

    public static class Notification {
        public static final String PRE_FIX = "/notification";
        public static final String GET_ALL = PRE_FIX + "/all";
        public static final String GET_UNREAD = PRE_FIX + "/unread";
        public static final String SEEN_ALL = PRE_FIX + "/seen";

        private Notification() {
        }
    }

}
