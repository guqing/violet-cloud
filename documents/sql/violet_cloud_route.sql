// ----------------------------
// 黑名单管理
// ----------------------------
db.getCollection("blackList").drop();
db.createCollection("blackList");

db.getCollection("blackList").insert([ {
    _id: ObjectId("5e1425e7a548e77106e43b40"),
    ip: "",
    requestUri: "/**/actuator/**",
    requestMethod: "ALL",
    limitFrom: "",
    limitTo: "",
    location: "",
    status: "1",
    createTime: "2020-07-17 18:01:07",
    _class: "BlackList"
} ]);

// ----------------------------
// 拦截日志
// ----------------------------
db.getCollection("blockLog").drop();
db.createCollection("blockLog");

// ----------------------------
// 限流日志
// ----------------------------
db.getCollection("rateLimitLog").drop();
db.createCollection("rateLimitLog");

// ----------------------------
// 限流规则
// ----------------------------
db.getCollection("rateLimitRule").drop();
db.createCollection("rateLimitRule");

db.getCollection("rateLimitRule").insert([ {
    _id: ObjectId("5e1abc9ef51708123d94b1f8"),
    requestUri: "/auth/captcha",
    requestMethod: "GET",
    limitFrom: "06:00:00",
    limitTo: "22:30:00",
    count: "3",
    intervalSec: "10",
    status: "1",
    createTime: "2020-07-17 18:01:46",
    _class: "RateLimitRule"
} ]);

// ----------------------------
// 访问日志
// ----------------------------
db.getCollection("routeLog").drop();
db.createCollection("routeLog");

// ----------------------------
// 网关用户
// ----------------------------
db.getCollection("routeUser").drop();
db.createCollection("routeUser");

// ----------------------------
// 网关用户基础数据 password: 123456
// ----------------------------
db.getCollection("routeUser").insert([ {
    _id: ObjectId("5e1d2ee055165e6516c23057"),
    username: "guqing",
    password: "$2a$10$FZmcHU9UUw8XN.MluZz2reWKu7N.8QhSDgfENTltSoHmHj6BzEoQO",
    roles: "user",
    createTime: "2020-07-17 18:00:48",
    _class: "RouteUser"
} ]);
db.getCollection("routeUser").insert([ {
    _id: ObjectId("5e1d2eee55165e6516c23058"),
    username: "admin",
    password: "$2a$10$FZmcHU9UUw8XN.MluZz2reWKu7N.8QhSDgfENTltSoHmHj6BzEoQO",
    roles: "admin",
    createTime: "2020-07-17 18:00:02",
    _class: "RouteUser"
} ]);
