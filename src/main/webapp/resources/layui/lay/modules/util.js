/** layui-v2.1.5 MIT License By http://www.layui.com */
;layui.define("jquery", function (e) {
    "use strict";
    var o = layui.$, t = {
        fixbar: function (e) {
            var t, a, i = "layui-fixbar", l = "layui-fixbar-top", r = o(document), c = o("body");
            e = o.extend({showHeight: 200}, e), e.bar1 = e.bar1 === !0 ? "&#xe606;" : e.bar1, e.bar2 = e.bar2 === !0 ? "&#xe607;" : e.bar2, e.bgcolor = e.bgcolor ? "background-color:" + e.bgcolor : "";
            var n = [e.bar1, e.bar2, "&#xe604;"],
                u = o(['<ul class="' + i + '">', e.bar1 ? '<li class="layui-icon" lay-type="bar1" style="' + e.bgcolor + '">' + n[0] + "</li>" : "", e.bar2 ? '<li class="layui-icon" lay-type="bar2" style="' + e.bgcolor + '">' + n[1] + "</li>" : "", '<li class="layui-icon ' + l + '" lay-type="top" style="' + e.bgcolor + '">' + n[2] + "</li>", "</ul>"].join("")),
                s = u.find("." + l), b = function () {
                    var o = r.scrollTop();
                    o >= e.showHeight ? t || (s.show(), t = 1) : t && (s.hide(), t = 0)
                };
            o("." + i)[0] || ("object" == typeof e.css && u.css(e.css), c.append(u), b(), u.find("li").on("click", function () {
                var t = o(this), a = t.attr("lay-type");
                "top" === a && o("html,body").animate({scrollTop: 0}, 200), e.click && e.click.call(this, a)
            }), r.on("scroll", function () {
                clearTimeout(a), a = setTimeout(function () {
                    b()
                }, 100)
            }))
        }, countdown: function (e, o, t) {
            var a = this, i = "function" == typeof o, l = new Date(e).getTime(),
                r = new Date(!o || i ? (new Date).getTime() : o).getTime(), c = l - r,
                n = [Math.floor(c / 864e5), Math.floor(c / 36e5) % 24, Math.floor(c / 6e4) % 60, Math.floor(c / 1e3) % 60];
            i && (t = o);
            var u = setTimeout(function () {
                a.countdown(e, r + 1e3, t)
            }, 1e3);
            return t && t(c > 0 ? n : [0, 0, 0, 0], o, u), c <= 0 && clearTimeout(u), u
        }, timeAgo: function (e, o) {
            var t = (new Date).getTime() - new Date(e).getTime();
            return t > 2592e6 ? (t = new Date(e).toLocaleString(), o && (t = t.replace(/\s[\S]+$/g, "")), t) : t >= 864e5 ? (t / 1e3 / 60 / 60 / 24 | 0) + "天前" : t >= 36e5 ? (t / 1e3 / 60 / 60 | 0) + "小时前" : t >= 18e4 ? (t / 1e3 / 60 | 0) + "分钟前" : t < 0 ? "未来" : "刚刚"
        }
    };
    e("util", t)
});