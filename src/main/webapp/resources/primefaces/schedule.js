/*
 * FullCalendar v2.2.5
 * Docs & License: http://arshaw.com/fullcalendar/
 * (c) 2013 Adam Shaw
 */
(function(a) {
    if (typeof define === "function" && define.amd) {
        define(["jquery", "moment"], a)
    } else {
        a(jQuery, moment)
    }
})(function(aK, z) {
    var T = {
        titleRangeSeparator: " \u2014 ",
        monthYearFormat: "MMMM YYYY",
        defaultTimedEventDuration: "02:00:00",
        defaultAllDayEventDuration: {
            days: 1
        },
        forceEventDuration: false,
        nextDayThreshold: "09:00:00",
        defaultView: "month",
        aspectRatio: 1.35,
        header: {
            left: "title",
            center: "",
            right: "today prev,next"
        },
        dayRender: function( date, cell ) {
        	var maxDate = new Date();
        	if (date > maxDate){
                $(cell).addClass('disabled');
            }
        },
        weekends: true,
        weekNumbers: false,
        weekNumberTitle: "W",
        weekNumberCalculation: "local",
        lazyFetching: true,
        startParam: "start",
        endParam: "end",
        timezoneParam: "timezone",
        timezone: false,
        isRTL: false,
        defaultButtonText: {
            prev: "prev",
            next: "next",
            prevYear: "prev year",
            nextYear: "next year",
            today: "today",
            month: "month",
            week: "week",
            day: "day"
        },
        buttonIcons: {
            prev: "left-single-arrow",
            next: "right-single-arrow",
            prevYear: "left-double-arrow",
            nextYear: "right-double-arrow"
        },
        theme: false,
        themeButtonIcons: {
            prev: "circle-triangle-w",
            next: "circle-triangle-e",
            prevYear: "seek-prev",
            nextYear: "seek-next"
        },
        dragOpacity: 0.75,
        dragRevertDuration: 500,
        dragScroll: true,
        unselectAuto: true,
        dropAccept: "*",
        eventLimit: false,
        eventLimitText: "more",
        eventLimitClick: "popover",
        dayPopoverFormat: "LL",
        handleWindowResize: true,
        windowResizeDelay: 200
    };
    var n = {
        dayPopoverFormat: "dddd, MMMM D"
    };
    var ab = {
        header: {
            left: "next,prev today",
            center: "",
            right: "title"
        },
        buttonIcons: {
            prev: "right-single-arrow",
            next: "left-single-arrow",
            prevYear: "right-double-arrow",
            nextYear: "left-double-arrow"
        },
        themeButtonIcons: {
            prev: "circle-triangle-e",
            next: "circle-triangle-w",
            nextYear: "seek-prev",
            prevYear: "seek-next"
        }
    };
    var aY = aK.fullCalendar = {
        version: "2.2.5"
    };
    var ax = aY.views = {};
    aK.fn.fullCalendar = function(a8) {
        var a7 = Array.prototype.slice.call(arguments, 1);
        var a9 = this;
        this.each(function(bc, ba) {
            var bb = aK(ba);
            var be = bb.data("fullCalendar");
            var bd;
            if (typeof a8 === "string") {
                if (be && aK.isFunction(be[a8])) {
                    bd = be[a8].apply(be, a7);
                    if (!bc) {
                        a9 = bd
                    }
                    if (a8 === "destroy") {
                        bb.removeData("fullCalendar")
                    }
                }
            } else {
                if (!be) {
                    be = new p(bb, a8);
                    bb.data("fullCalendar", be);
                    be.render()
                }
            }
        });
        $(".fc-holiday").html('FERIADO');
        return a9
    };

    function aC(a7) {
        aV(T, a7)
    }

    function aV(a9) {
        function a8(ba, bb) {
            if (aK.isPlainObject(bb) && aK.isPlainObject(a9[ba]) && !ac(ba)) {
                a9[ba] = aV({}, a9[ba], bb)
            } else {
                if (bb !== undefined) {
                    a9[ba] = bb
                }
            }
        }
        for (var a7 = 1; a7 < arguments.length; a7++) {
            aK.each(arguments[a7], a8)
        }
        return a9
    }

    function ac(a7) {
        return /(Time|Duration)$/.test(a7)
    }
    var aM = aY.langs = {};
    aY.datepickerLang = function(ba, a8, a9) {
        var a7 = aM[ba] || (aM[ba] = {});
        a7.isRTL = a9.isRTL;
        a7.weekNumberTitle = a9.weekHeader;
        aK.each(ad, function(bb, bc) {
            a7[bb] = bc(a9)
        });
        if (aK.datepicker) {
            aK.datepicker.regional[a8] = aK.datepicker.regional[ba] = a9;
            aK.datepicker.regional.en = aK.datepicker.regional[""];
            aK.datepicker.setDefaults(a9)
        }
    };
    aY.lang = function(ba, a9) {
        var a7;
        var a8;
        a7 = aM[ba] || (aM[ba] = {});
        if (a9) {
            aV(a7, a9)
        }
        a8 = Z(ba);
        aK.each(l, function(bb, bc) {
            if (a7[bb] === undefined) {
                a7[bb] = bc(a8, a7)
            }
        });
        T.lang = ba
    };
    var ad = {
        defaultButtonText: function(a7) {
            return {
                prev: af(a7.prevText),
                next: af(a7.nextText),
                today: af(a7.currentText)
            }
        },
        monthYearFormat: function(a7) {
            return a7.showMonthAfterYear ? "YYYY[" + a7.yearSuffix + "] MMMM" : "MMMM YYYY[" + a7.yearSuffix + "]"
        }
    };
    var l = {
        dayOfMonthFormat: function(a8, a7) {
            var a9 = a8.longDateFormat("l");
            a9 = a9.replace(/^Y+[^\w\s]*|[^\w\s]*Y+$/g, "");
            if (a7.isRTL) {
                a9 += " ddd"
            } else {
                //a9 = "ddd " + a9
            	a9 = "ddd D/M"
            }
            return a9
        },
        smallTimeFormat: function(a7) {
            return a7.longDateFormat("LT").replace(":mm", "(:mm)").replace(/(\Wmm)$/, "($1)").replace(/\s*a$/i, "a")
        },
        extraSmallTimeFormat: function(a7) {
            return a7.longDateFormat("LT").replace(":mm", "(:mm)").replace(/(\Wmm)$/, "($1)").replace(/\s*a$/i, "t")
        },
        noMeridiemTimeFormat: function(a7) {
            return a7.longDateFormat("LT").replace(/\s*a$/i, "")
        }
    };

    function Z(a8) {
        var a7 = z.localeData || z.langData;
        return a7.call(z, a8) || a7.call(z, "en")
    }
    aY.lang("en", n);
    aY.intersectionToSeg = aI;
    aY.applyAll = N;
    aY.debounce = a6;

    function aW(a8, a7) {
        if (a7.left) {
            a8.css({
                "border-left-width": 1,
                "margin-left": a7.left - 1
            })
        }
        if (a7.right) {
            a8.css({
                "border-right-width": 1,
                "margin-right": a7.right - 1
            })
        }
    }

    function al(a7) {
        a7.css({
            "margin-left": "",
            "margin-right": "",
            "border-left-width": "",
            "border-right-width": ""
        })
    }

    function ar() {
        aK("body").addClass("fc-not-allowed")
    }

    function A() {
        aK("body").removeClass("fc-not-allowed")
    }

    function G(bc, bb, a8) {
        var a9 = Math.floor(bb / bc.length);
        var a7 = Math.floor(bb - a9 * (bc.length - 1));
        var bd = [];
        var be = [];
        var ba = [];
        var bf = 0;
        c(bc);
        bc.each(function(bh, bi) {
            var bj = bh === bc.length - 1 ? a7 : a9;
            var bg = aK(bi).outerHeight(true);
            if (bg < bj) {
                bd.push(bi);
                be.push(bg);
                ba.push(aK(bi).height())
            } else {
                bf += bg
            }
        });
        if (a8) {
            bb -= bf;
            a9 = Math.floor(bb / bd.length);
            a7 = Math.floor(bb - a9 * (bd.length - 1))
        }
        aK(bd).each(function(bi, bj) {
            var bl = bi === bd.length - 1 ? a7 : a9;
            var bh = be[bi];
            var bk = ba[bi];
            var bg = bl - (bh - bk);
            if (bh < bl) {
                aK(bj).height(bg)
            }
        })
    }

    function c(a7) {
        a7.height("")
    }

    function J(a7) {
        var a8 = 0;
        a7.find("> *").each(function(ba, bb) {
            var a9 = aK(bb).outerWidth();
            if (a9 > a8) {
                a8 = a9
            }
        });
        a8++;
        a7.width(a8);
        return a8
    }

    function aX(a8, a7) {
        a8.height(a7).addClass("fc-scroller");
        if (a8[0].scrollHeight - 1 > a8[0].clientHeight) {
            return true
        }
        r(a8);
        return false
    }

    function r(a7) {
        a7.height("").removeClass("fc-scroller")
    }

    function ap(a8) {
        var a7 = a8.css("position"),
            a9 = a8.parents().filter(function() {
                var ba = aK(this);
                return (/(auto|scroll)/).test(ba.css("overflow") + ba.css("overflow-y") + ba.css("overflow-x"))
            }).eq(0);
        return a7 === "fixed" || !a9.length ? aK(a8[0].ownerDocument || document) : a9
    }

    function j(a7) {
        var a9 = a7.offset().left;
        var bb = a9 + a7.width();
        var a8 = a7.children();
        var ba = a8.offset().left;
        var bc = ba + a8.outerWidth();
        return {
            left: ba - a9,
            right: bb - bc
        }
    }

    function V(a7) {
        return a7.which == 1 && !a7.ctrlKey
    }

    function aI(bf, a8) {
        var bb = bf.start;
        var a9 = bf.end;
        var be = a8.start;
        var bc = a8.end;
        var bd, bg;
        var a7, ba;
        if (a9 > be && bb < bc) {
            if (bb >= be) {
                bd = bb.clone();
                a7 = true
            } else {
                bd = be.clone();
                a7 = false
            }
            if (a9 <= bc) {
                bg = a9.clone();
                ba = true
            } else {
                bg = bc.clone();
                ba = false
            }
            return {
                start: bd,
                end: bg,
                isStart: a7,
                isEnd: ba
            }
        }
    }

    function H(bb, a7) {
        bb = bb || {};
        if (bb[a7] !== undefined) {
            return bb[a7]
        }
        var ba = a7.split(/(?=[A-Z])/),
            a9 = ba.length - 1,
            a8;
        for (; a9 >= 0; a9--) {
            a8 = bb[ba[a9].toLowerCase()];
            if (a8 !== undefined) {
                return a8
            }
        }
        return bb["default"]
    }
    var aa = ["sun", "mon", "tue", "wed", "thu", "fri", "sat"];
    var i = ["year", "month", "week", "day", "hour", "minute", "second", "millisecond"];

    function t(a8, a7) {
        return z.duration({
            days: a8.clone().stripTime().diff(a7.clone().stripTime(), "days"),
            ms: a8.time() - a7.time()
        })
    }

    function U(a8, a7) {
        return z.duration({
            days: a8.clone().stripTime().diff(a7.clone().stripTime(), "days")
        })
    }

    function M(ba, a7) {
        var a8, a9;
        for (a8 = 0; a8 < i.length; a8++) {
            a9 = i[a8];
            if (aE(a9, ba, a7)) {
                break
            }
        }
        return a9
    }

    function aE(a8, ba, a7) {
        var a9;
        if (a7 != null) {
            a9 = a7.diff(ba, a8, true)
        } else {
            if (z.isDuration(ba)) {
                a9 = ba.as(a8)
            } else {
                a9 = ba.end.diff(ba.start, a8, true)
            }
        }
        if (a9 >= 1 && P(a9)) {
            return a9
        }
        return false
    }

    function K(a7) {
        return Object.prototype.toString.call(a7) === "[object Date]" || a7 instanceof Date
    }

    function L(a7) {
        return /^\d+\:\d+(?:\:\d+\.?(?:\d{3})?)?$/.test(a7)
    }
    var u = {}.hasOwnProperty;

    function R(a7) {
        var a8 = function() {};
        a8.prototype = a7;
        return new a8()
    }

    function aT(a9, a8) {
        for (var a7 in a9) {
            if (aH(a9, a7)) {
                a8[a7] = a9[a7]
            }
        }
    }

    function aH(a8, a7) {
        return u.call(a8, a7)
    }

    function aA(a7) {
        return /undefined|null|boolean|number|string/.test(aK.type(a7))
    }

    function N(ba, bb, a8) {
        if (aK.isFunction(ba)) {
            ba = [ba]
        }
        if (ba) {
            var a9;
            var a7;
            for (a9 = 0; a9 < ba.length; a9++) {
                a7 = ba[a9].apply(bb, a8) || a7
            }
            return a7
        }
    }

    function a4() {
        for (var a7 = 0; a7 < arguments.length; a7++) {
            if (arguments[a7] !== undefined) {
                return arguments[a7]
            }
        }
    }

    function a2(a7) {
        return (a7 + "").replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/'/g, "&#039;").replace(/"/g, "&quot;").replace(/\n/g, "<br />")
    }

    function af(a7) {
        return a7.replace(/&.*?;/g, "")
    }

    function aQ(a7) {
        return a7.charAt(0).toUpperCase() + a7.slice(1)
    }

    function aj(a8, a7) {
        return a8 - a7
    }

    function P(a7) {
        return a7 % 1 === 0
    }

    function a6(ba, bc) {
        var bd;
        var a8;
        var a9;
        var bb;
        var a7 = function() {
            var be = +new Date() - bb;
            if (be < bc && be > 0) {
                bd = setTimeout(a7, bc - be)
            } else {
                bd = null;
                ba.apply(a9, a8);
                if (!bd) {
                    a9 = a8 = null
                }
            }
        };
        return function() {
            a9 = this;
            a8 = arguments;
            bb = +new Date();
            if (!bd) {
                bd = setTimeout(a7, bc)
            }
        }
    }
    var v = /^\s*\d{4}-\d\d$/;
    var S = /^\s*\d{4}-(?:(\d\d-\d\d)|(W\d\d$)|(W\d\d-\d)|(\d\d\d))((T| )(\d\d(:\d\d(:\d\d(\.\d+)?)?)?)?)?$/;
    var W = z.fn;
    var aG = aK.extend({}, W);
    var aL;
    var f;
    var ag;
    aY.moment = function() {
        return w(arguments)
    };
    aY.moment.utc = function() {
        var a7 = w(arguments, true);
        if (a7.hasTime()) {
            a7.utc()
        }
        return a7
    };
    aY.moment.parseZone = function() {
        return w(arguments, true, true)
    };

    function w(bd, bc, bb) {
        var bf = bd[0];
        var ba = bd.length == 1 && typeof bf === "string";
        var a9;
        var be;
        var a7;
        var a8;
        if (z.isMoment(bf)) {
            a8 = z.apply(null, bd);
            q(bf, a8)
        } else {
            if (K(bf) || bf === undefined) {
                a8 = z.apply(null, bd)
            } else {
                a9 = false;
                be = false;
                if (ba) {
                    if (v.test(bf)) {
                        bf += "-01";
                        bd = [bf];
                        a9 = true;
                        be = true
                    } else {
                        if ((a7 = S.exec(bf))) {
                            a9 = !a7[5];
                            be = true
                        }
                    }
                } else {
                    if (aK.isArray(bf)) {
                        be = true
                    }
                }
                if (bc || a9) {
                    a8 = z.utc.apply(z, bd)
                } else {
                    a8 = z.apply(null, bd)
                }
                if (a9) {
                    a8._ambigTime = true;
                    a8._ambigZone = true
                } else {
                    if (bb) {
                        if (be) {
                            a8._ambigZone = true
                        } else {
                            if (ba) {
                                a8.zone(bf)
                            }
                        }
                    }
                }
            }
        }
        a8._fullCalendar = true;
        return a8
    }
    W.clone = function() {
        var a7 = aG.clone.apply(this, arguments);
        q(this, a7);
        if (this._fullCalendar) {
            a7._fullCalendar = true
        }
        return a7
    };
    W.time = function(a8) {
        if (!this._fullCalendar) {
            return aG.time.apply(this, arguments)
        }
        if (a8 == null) {
            return z.duration({
                hours: this.hours(),
                minutes: this.minutes(),
                seconds: this.seconds(),
                milliseconds: this.milliseconds()
            })
        } else {
            this._ambigTime = false;
            if (!z.isDuration(a8) && !z.isMoment(a8)) {
                a8 = z.duration(a8)
            }
            var a7 = 0;
            if (z.isDuration(a8)) {
                a7 = Math.floor(a8.asDays()) * 24
            }
            return this.hours(a7 + a8.hours()).minutes(a8.minutes()).seconds(a8.seconds()).milliseconds(a8.milliseconds())
        }
    };
    W.stripTime = function() {
        var a7;
        if (!this._ambigTime) {
            a7 = this.toArray();
            this.utc();
            f(this, a7.slice(0, 3));
            this._ambigTime = true;
            this._ambigZone = true
        }
        return this
    };
    W.hasTime = function() {
        return !this._ambigTime
    };
    W.stripZone = function() {
        var a7, a8;
        if (!this._ambigZone) {
            a7 = this.toArray();
            a8 = this._ambigTime;
            this.utc();
            f(this, a7);
            if (a8) {
                this._ambigTime = true
            }
            this._ambigZone = true
        }
        return this
    };
    W.hasZone = function() {
        return !this._ambigZone
    };
    W.zone = function(a7) {
        if (a7 != null) {
            this._ambigTime = false;
            this._ambigZone = false
        }
        return aG.zone.apply(this, arguments)
    };
    W.local = function() {
        var a7 = this.toArray();
        var a8 = this._ambigZone;
        aG.local.apply(this, arguments);
        if (a8) {
            ag(this, a7)
        }
        return this
    };
    W.format = function() {
        if (this._fullCalendar && arguments[0]) {
            return E(this, arguments[0])
        }
        if (this._ambigTime) {
            return e(this, "YYYY-MM-DD")
        }
        if (this._ambigZone) {
            return e(this, "YYYY-MM-DD[T]HH:mm:ss")
        }
        return aG.format.apply(this, arguments)
    };
    W.toISOString = function() {
        if (this._ambigTime) {
            return e(this, "YYYY-MM-DD")
        }
        if (this._ambigZone) {
            return e(this, "YYYY-MM-DD[T]HH:mm:ss")
        }
        return aG.toISOString.apply(this, arguments)
    };
    W.isWithin = function(a9, a8) {
        var a7 = aF([this, a9, a8]);
        return a7[0] >= a7[1] && a7[0] < a7[2]
    };
    W.isSame = function(a9, a8) {
        var a7;
        if (!this._fullCalendar) {
            return aG.isSame.apply(this, arguments)
        }
        if (a8) {
            a7 = aF([this, a9], true);
            return aG.isSame.call(a7[0], a7[1], a8)
        } else {
            a9 = aY.moment.parseZone(a9);
            return aG.isSame.call(this, a9) && Boolean(this._ambigTime) === Boolean(a9._ambigTime) && Boolean(this._ambigZone) === Boolean(a9._ambigZone)
        }
    };
    aK.each(["isBefore", "isAfter"], function(a8, a7) {
        W[a7] = function(bb, ba) {
            var a9;
            if (!this._fullCalendar) {
                return aG[a7].apply(this, arguments)
            }
            a9 = aF([this, bb]);
            return aG[a7].call(a9[0], a9[1], ba)
        }
    });

    function aF(a8, be) {
        var ba = false;
        var bd = false;
        var a7 = a8.length;
        var a9 = [];
        var bb, bc;
        for (bb = 0; bb < a7; bb++) {
            bc = a8[bb];
            if (!z.isMoment(bc)) {
                bc = aY.moment.parseZone(bc)
            }
            ba = ba || bc._ambigTime;
            bd = bd || bc._ambigZone;
            a9.push(bc)
        }
        for (bb = 0; bb < a7; bb++) {
            bc = a9[bb];
            if (!be && ba && !bc._ambigTime) {
                a9[bb] = bc.clone().stripTime()
            } else {
                if (bd && !bc._ambigZone) {
                    a9[bb] = bc.clone().stripZone()
                }
            }
        }
        return a9
    }

    function q(a8, a7) {
        if (a8._ambigTime) {
            a7._ambigTime = true
        } else {
            if (a7._ambigTime) {
                a7._ambigTime = false
            }
        }
        if (a8._ambigZone) {
            a7._ambigZone = true
        } else {
            if (a7._ambigZone) {
                a7._ambigZone = false
            }
        }
    }

    function a3(a8, a7) {
        a8.year(a7[0] || 0).month(a7[1] || 0).date(a7[2] || 0).hours(a7[3] || 0).minutes(a7[4] || 0).seconds(a7[5] || 0).milliseconds(a7[6] || 0)
    }
    aL = "_d" in z() && "updateOffset" in z;
    f = aL ? function(a8, a7) {
        a8._d.setTime(Date.UTC.apply(Date, a7));
        z.updateOffset(a8, false)
    } : a3;
    ag = aL ? function(a8, a7) {
        a8._d.setTime(+new Date(a7[0] || 0, a7[1] || 0, a7[2] || 0, a7[3] || 0, a7[4] || 0, a7[5] || 0, a7[6] || 0));
        z.updateOffset(a8, false)
    } : a3;

    function e(a8, a7) {
        return aG.format.call(a8, a7)
    }

    function E(a8, a7) {
        return aZ(a8, s(a7))
    }

    function aZ(a7, ba) {
        var a9 = "";
        var a8;
        for (a8 = 0; a8 < ba.length; a8++) {
            a9 += Y(a7, ba[a8])
        }
        return a9
    }
    var d = {
        t: function(a7) {
            return e(a7, "a").charAt(0)
        },
        T: function(a7) {
            return e(a7, "A").charAt(0)
        }
    };

    function Y(a8, a7) {
        var a9;
        var ba;
        if (typeof a7 === "string") {
            return a7
        } else {
            if ((a9 = a7.token)) {
                if (d[a9]) {
                    return d[a9](a8)
                }
                return e(a8, a9)
            } else {
                if (a7.maybe) {
                    ba = aZ(a8, a7.maybe);
                    if (ba.match(/[1-9]/)) {
                        return ba
                    }
                }
            }
        }
        return ""
    }

    function O(bc, ba, a7, bb, a8) {
        var a9;
        bc = aY.moment.parseZone(bc);
        ba = aY.moment.parseZone(ba);
        a9 = (bc.localeData || bc.lang).call(bc);
        a7 = a9.longDateFormat(a7) || a7;
        bb = bb || " - ";
        return aw(bc, ba, s(a7), bb, a8)
    }
    aY.formatRange = O;

    function aw(bk, bi, bc, bb, a8) {
        var bg;
        var bj;
        var ba = "";
        var a7;
        var be = "";
        var bh;
        var bf = "";
        var bd = "";
        var a9 = "";
        for (bj = 0; bj < bc.length; bj++) {
            bg = aJ(bk, bi, bc[bj]);
            if (bg === false) {
                break
            }
            ba += bg
        }
        for (a7 = bc.length - 1; a7 > bj; a7--) {
            bg = aJ(bk, bi, bc[a7]);
            if (bg === false) {
                break
            }
            be = bg + be
        }
        for (bh = bj; bh <= a7; bh++) {
            bf += Y(bk, bc[bh]);
            bd += Y(bi, bc[bh])
        }
        if (bf || bd) {
            if (a8) {
                a9 = bd + bb + bf
            } else {
                a9 = bf + bb + bd
            }
        }
        return ba + a9 + be
    }
    var aU = {
        Y: "year",
        M: "month",
        D: "day",
        d: "day",
        A: "second",
        a: "second",
        T: "second",
        t: "second",
        H: "second",
        h: "second",
        m: "second",
        s: "second"
    };

    function aJ(bb, ba, a7) {
        var a8;
        var a9;
        if (typeof a7 === "string") {
            return a7
        } else {
            if ((a8 = a7.token)) {
                a9 = aU[a8.charAt(0)];
                if (a9 && bb.isSame(ba, a9)) {
                    return e(bb, a8)
                }
            }
        }
        return false
    }
    var a0 = {};

    function s(a7) {
        if (a7 in a0) {
            return a0[a7]
        }
        return (a0[a7] = ao(a7))
    }

    function ao(a8) {
        var ba = [];
        var a7 = /\[([^\]]*)\]|\(([^\)]*)\)|(LT|(\w)\4*o?)|([^\w\[\(]+)/g;
        var a9;
        while ((a9 = a7.exec(a8))) {
            if (a9[1]) {
                ba.push(a9[1])
            } else {
                if (a9[2]) {
                    ba.push({
                        maybe: ao(a9[2])
                    })
                } else {
                    if (a9[3]) {
                        ba.push({
                            token: a9[3]
                        })
                    } else {
                        if (a9[5]) {
                            ba.push(a9[5])
                        }
                    }
                }
            }
        }
        return ba
    }
    aY.Class = aR;

    function aR() {}
    aR.extend = function(a7) {
        var a8 = this;
        var a9;
        a7 = a7 || {};
        if (aH(a7, "constructor")) {
            a9 = a7.constructor
        }
        if (typeof a9 !== "function") {
            a9 = a7.constructor = function() {
                a8.apply(this, arguments)
            }
        }
        a9.prototype = R(a8.prototype);
        aT(a7, a9.prototype);
        aT(a8, a9);
        return a9
    };
    aR.mixin = function(a7) {
        aT(a7.prototype || a7, this.prototype)
    };
    var k = aR.extend({
        isHidden: true,
        options: null,
        el: null,
        documentMousedownProxy: null,
        margin: 10,
        constructor: function(a7) {
            this.options = a7 || {}
        },
        show: function() {
            if (this.isHidden) {
                if (!this.el) {
                    this.render()
                }
                this.el.show();
                this.position();
                this.isHidden = false;
                this.trigger("show")
            }
        },
        hide: function() {
            if (!this.isHidden) {
                this.el.hide();
                this.isHidden = true;
                this.trigger("hide")
            }
        },
        render: function() {
            var a8 = this;
            var a7 = this.options;
            this.el = aK('<div class="fc-popover"/>').addClass(a7.className || "").css({
                top: 0,
                left: 0
            }).append(a7.content).appendTo(a7.parentEl);
            this.el.on("click", ".fc-close", function() {
                a8.hide()
            });
            if (a7.autoHide) {
                aK(document).on("mousedown", this.documentMousedownProxy = aK.proxy(this, "documentMousedown"))
            }
        },
        documentMousedown: function(a7) {
            if (this.el && !aK(a7.target).closest(this.el).length) {
                this.hide()
            }
        },
        destroy: function() {
            this.hide();
            if (this.el) {
                this.el.remove();
                this.el = null
            }
            aK(document).off("mousedown", this.documentMousedownProxy)
        },
        position: function() {
            var bh = this.options;
            var bd = this.el.offsetParent().offset();
            var a7 = this.el.outerWidth();
            var bf = this.el.outerHeight();
            var be = aK(window);
            var bc = ap(this.el);
            var a8;
            var ba;
            var bg;
            var bb;
            var a9;
            bb = bh.top || 0;
            if (bh.left !== undefined) {
                a9 = bh.left
            } else {
                if (bh.right !== undefined) {
                    a9 = bh.right - a7
                } else {
                    a9 = 0
                }
            }
            if (bc.is(window) || bc.is(document)) {
                bc = be;
                a8 = 0;
                ba = 0
            } else {
                bg = bc.offset();
                a8 = bg.top;
                ba = bg.left
            }
            a8 += be.scrollTop();
            ba += be.scrollLeft();
            if (bh.viewportConstrain !== false) {
                bb = Math.min(bb, a8 + bc.outerHeight() - bf - this.margin);
                bb = Math.max(bb, a8 + this.margin);
                a9 = Math.min(a9, ba + bc.outerWidth() - a7 - this.margin);
                a9 = Math.max(a9, ba + this.margin)
            }
            this.el.css({
                top: bb - bd.top,
                left: a9 - bd.left
            })
        },
        trigger: function(a7) {
            if (this.options[a7]) {
                this.options[a7].apply(this, Array.prototype.slice.call(arguments, 1))
            }
        }
    });
    var ah = aR.extend({
        grid: null,
        rowCoords: null,
        colCoords: null,
        containerEl: null,
        minX: null,
        maxX: null,
        minY: null,
        maxY: null,
        constructor: function(a7) {
            this.grid = a7
        },
        build: function() {
            this.rowCoords = this.grid.computeRowCoords();
            this.colCoords = this.grid.computeColCoords();
            this.computeBounds()
        },
        clear: function() {
            this.rowCoords = null;
            this.colCoords = null
        },
        getCell: function(bd, ba) {
            var bf = this.rowCoords;
            var a8 = this.colCoords;
            var a9 = null;
            var bc = null;
            var a7, bb;
            var be;
            if (this.inBounds(bd, ba)) {
                for (a7 = 0; a7 < bf.length; a7++) {
                    bb = bf[a7];
                    if (ba >= bb.top && ba < bb.bottom) {
                        a9 = a7;
                        break
                    }
                }
                for (a7 = 0; a7 < a8.length; a7++) {
                    bb = a8[a7];
                    if (bd >= bb.left && bd < bb.right) {
                        bc = a7;
                        break
                    }
                }
                if (a9 !== null && bc !== null) {
                    be = this.grid.getCell(a9, bc);
                    be.grid = this.grid;
                    return be
                }
            }
            return null
        },
        computeBounds: function() {
            var a7;
            if (this.containerEl) {
                a7 = this.containerEl.offset();
                this.minX = a7.left;
                this.maxX = a7.left + this.containerEl.outerWidth();
                this.minY = a7.top;
                this.maxY = a7.top + this.containerEl.outerHeight()
            }
        },
        inBounds: function(a7, a8) {
            if (this.containerEl) {
                return a7 >= this.minX && a7 < this.maxX && a8 >= this.minY && a8 < this.maxY
            }
            return true
        }
    });
    var D = aR.extend({
        coordMaps: null,
        constructor: function(a7) {
            this.coordMaps = a7
        },
        build: function() {
            var a8 = this.coordMaps;
            var a7;
            for (a7 = 0; a7 < a8.length; a7++) {
                a8[a7].build()
            }
        },
        getCell: function(a8, bb) {
            var ba = this.coordMaps;
            var a7 = null;
            var a9;
            for (a9 = 0; a9 < ba.length && !a7; a9++) {
                a7 = ba[a9].getCell(a8, bb)
            }
            return a7
        },
        clear: function() {
            var a8 = this.coordMaps;
            var a7;
            for (a7 = 0; a7 < a8.length; a7++) {
                a8[a7].clear()
            }
        }
    });
    var aP = aR.extend({
        coordMap: null,
        options: null,
        isListening: false,
        isDragging: false,
        origCell: null,
        cell: null,
        mouseX0: null,
        mouseY0: null,
        mousemoveProxy: null,
        mouseupProxy: null,
        scrollEl: null,
        scrollBounds: null,
        scrollTopVel: null,
        scrollLeftVel: null,
        scrollIntervalId: null,
        scrollHandlerProxy: null,
        scrollSensitivity: 30,
        scrollSpeed: 200,
        scrollIntervalMs: 50,
        constructor: function(a8, a7) {
            this.coordMap = a8;
            this.options = a7 || {}
        },
        mousedown: function(a7) {
            if (V(a7)) {
                a7.preventDefault();
                this.startListening(a7);
                if (!this.options.distance) {
                    this.startDrag(a7)
                }
            }
        },
        startListening: function(a8) {
            var a9;
            var a7;
            if (!this.isListening) {
                if (a8 && this.options.scroll) {
                    a9 = ap(aK(a8.target));
                    if (!a9.is(window) && !a9.is(document)) {
                        this.scrollEl = a9;
                        this.scrollHandlerProxy = a6(aK.proxy(this, "scrollHandler"), 100);
                        this.scrollEl.on("scroll", this.scrollHandlerProxy)
                    }
                }
                this.computeCoords();
                if (a8) {
                    a7 = this.getCell(a8);
                    this.origCell = a7;
                    this.mouseX0 = a8.pageX;
                    this.mouseY0 = a8.pageY
                }
                aK(document).on("mousemove", this.mousemoveProxy = aK.proxy(this, "mousemove")).on("mouseup", this.mouseupProxy = aK.proxy(this, "mouseup")).on("selectstart", this.preventDefault);
                this.isListening = true;
                this.trigger("listenStart", a8)
            }
        },
        computeCoords: function() {
            this.coordMap.build();
            this.computeScrollBounds()
        },
        mousemove: function(a8) {
            var a9;
            var a7;
            if (!this.isDragging) {
                a9 = this.options.distance || 1;
                a7 = Math.pow(a8.pageX - this.mouseX0, 2) + Math.pow(a8.pageY - this.mouseY0, 2);
                if (a7 >= a9 * a9) {
                    this.startDrag(a8)
                }
            }
            if (this.isDragging) {
                this.drag(a8)
            }
        },
        startDrag: function(a8) {
            var a7;
            if (!this.isListening) {
                this.startListening()
            }
            if (!this.isDragging) {
                this.isDragging = true;
                this.trigger("dragStart", a8);
                a7 = this.getCell(a8);
                if (a7) {
                    this.cellOver(a7)
                }
            }
        },
        drag: function(a8) {
            var a7;
            if (this.isDragging) {
                a7 = this.getCell(a8);
                if (!I(a7, this.cell)) {
                    if (this.cell) {
                        this.cellOut()
                    }
                    if (a7) {
                        this.cellOver(a7)
                    }
                }
                this.dragScroll(a8)
            }
        },
        cellOver: function(a7) {
            this.cell = a7;
            this.trigger("cellOver", a7, I(a7, this.origCell))
        },
        cellOut: function() {
            if (this.cell) {
                this.trigger("cellOut", this.cell);
                this.cell = null
            }
        },
        mouseup: function(a7) {
            this.stopDrag(a7);
            this.stopListening(a7)
        },
        stopDrag: function(a7) {
            if (this.isDragging) {
                this.stopScrolling();
                this.trigger("dragStop", a7);
                this.isDragging = false
            }
        },
        stopListening: function(a7) {
            if (this.isListening) {
                if (this.scrollEl) {
                    this.scrollEl.off("scroll", this.scrollHandlerProxy);
                    this.scrollHandlerProxy = null
                }
                aK(document).off("mousemove", this.mousemoveProxy).off("mouseup", this.mouseupProxy).off("selectstart", this.preventDefault);
                this.mousemoveProxy = null;
                this.mouseupProxy = null;
                this.isListening = false;
                this.trigger("listenStop", a7);
                this.origCell = this.cell = null;
                this.coordMap.clear()
            }
        },
        getCell: function(a7) {
            return this.coordMap.getCell(a7.pageX, a7.pageY)
        },
        trigger: function(a7) {
            if (this.options[a7]) {
                this.options[a7].apply(this, Array.prototype.slice.call(arguments, 1))
            }
        },
        preventDefault: function(a7) {
            a7.preventDefault()
        },
        computeScrollBounds: function() {
            var a7 = this.scrollEl;
            var a8;
            if (a7) {
                a8 = a7.offset();
                this.scrollBounds = {
                    top: a8.top,
                    left: a8.left,
                    bottom: a8.top + a7.outerHeight(),
                    right: a8.left + a7.outerWidth()
                }
            }
        },
        dragScroll: function(bd) {
            var a9 = this.scrollSensitivity;
            var a7 = this.scrollBounds;
            var a8, ba;
            var be, bf;
            var bc = 0;
            var bb = 0;
            if (a7) {
                a8 = (a9 - (bd.pageY - a7.top)) / a9;
                ba = (a9 - (a7.bottom - bd.pageY)) / a9;
                be = (a9 - (bd.pageX - a7.left)) / a9;
                bf = (a9 - (a7.right - bd.pageX)) / a9;
                if (a8 >= 0 && a8 <= 1) {
                    bc = a8 * this.scrollSpeed * -1
                } else {
                    if (ba >= 0 && ba <= 1) {
                        bc = ba * this.scrollSpeed
                    }
                }
                if (be >= 0 && be <= 1) {
                    bb = be * this.scrollSpeed * -1
                } else {
                    if (bf >= 0 && bf <= 1) {
                        bb = bf * this.scrollSpeed
                    }
                }
            }
            this.setScrollVel(bc, bb)
        },
        setScrollVel: function(a8, a7) {
            this.scrollTopVel = a8;
            this.scrollLeftVel = a7;
            this.constrainScrollVel();
            if ((this.scrollTopVel || this.scrollLeftVel) && !this.scrollIntervalId) {
                this.scrollIntervalId = setInterval(aK.proxy(this, "scrollIntervalFunc"), this.scrollIntervalMs)
            }
        },
        constrainScrollVel: function() {
            var a7 = this.scrollEl;
            if (this.scrollTopVel < 0) {
                if (a7.scrollTop() <= 0) {
                    this.scrollTopVel = 0
                }
            } else {
                if (this.scrollTopVel > 0) {
                    if (a7.scrollTop() + a7[0].clientHeight >= a7[0].scrollHeight) {
                        this.scrollTopVel = 0
                    }
                }
            }
            if (this.scrollLeftVel < 0) {
                if (a7.scrollLeft() <= 0) {
                    this.scrollLeftVel = 0
                }
            } else {
                if (this.scrollLeftVel > 0) {
                    if (a7.scrollLeft() + a7[0].clientWidth >= a7[0].scrollWidth) {
                        this.scrollLeftVel = 0
                    }
                }
            }
        },
        scrollIntervalFunc: function() {
            var a8 = this.scrollEl;
            var a7 = this.scrollIntervalMs / 1000;
            if (this.scrollTopVel) {
                a8.scrollTop(a8.scrollTop() + this.scrollTopVel * a7)
            }
            if (this.scrollLeftVel) {
                a8.scrollLeft(a8.scrollLeft() + this.scrollLeftVel * a7)
            }
            this.constrainScrollVel();
            if (!this.scrollTopVel && !this.scrollLeftVel) {
                this.stopScrolling()
            }
        },
        stopScrolling: function() {
            if (this.scrollIntervalId) {
                clearInterval(this.scrollIntervalId);
                this.scrollIntervalId = null;
                this.computeCoords()
            }
        },
        scrollHandler: function() {
            if (!this.scrollIntervalId) {
                this.computeCoords()
            }
        }
    });

    function I(a8, a7) {
        if (!a8 && !a7) {
            return true
        }
        if (a8 && a7) {
            return a8.grid === a7.grid && a8.row === a7.row && a8.col === a7.col
        }
        return false
    }
    var C = aR.extend({
        options: null,
        sourceEl: null,
        el: null,
        parentEl: null,
        top0: null,
        left0: null,
        mouseY0: null,
        mouseX0: null,
        topDelta: null,
        leftDelta: null,
        mousemoveProxy: null,
        isFollowing: false,
        isHidden: false,
        isAnimating: false,
        constructor: function(a7, a8) {
            this.options = a8 = a8 || {};
            this.sourceEl = a7;
            this.parentEl = a8.parentEl ? aK(a8.parentEl) : a7.parent()
        },
        start: function(a7) {
            if (!this.isFollowing) {
                this.isFollowing = true;
                this.mouseY0 = a7.pageY;
                this.mouseX0 = a7.pageX;
                this.topDelta = 0;
                this.leftDelta = 0;
                if (!this.isHidden) {
                    this.updatePosition()
                }
                aK(document).on("mousemove", this.mousemoveProxy = aK.proxy(this, "mousemove"))
            }
        },
        stop: function(a8, bb) {
            var ba = this;
            var a9 = this.options.revertDuration;

            function a7() {
                this.isAnimating = false;
                ba.destroyEl();
                this.top0 = this.left0 = null;
                if (bb) {
                    bb()
                }
            }
            if (this.isFollowing && !this.isAnimating) {
                this.isFollowing = false;
                aK(document).off("mousemove", this.mousemoveProxy);
                if (a8 && a9 && !this.isHidden) {
                    this.isAnimating = true;
                    this.el.animate({
                        top: this.top0,
                        left: this.left0
                    }, {
                        duration: a9,
                        complete: a7
                    })
                } else {
                    a7()
                }
            }
        },
        getEl: function() {
            var a7 = this.el;
            if (!a7) {
                this.sourceEl.width();
                a7 = this.el = this.sourceEl.clone().css({
                    position: "absolute",
                    visibility: "",
                    display: this.isHidden ? "none" : "",
                    margin: 0,
                    right: "auto",
                    bottom: "auto",
                    width: this.sourceEl.width(),
                    height: this.sourceEl.height(),
                    opacity: this.options.opacity || "",
                    zIndex: this.options.zIndex
                }).appendTo(this.parentEl)
            }
            return a7
        },
        destroyEl: function() {
            if (this.el) {
                this.el.remove();
                this.el = null
            }
        },
        updatePosition: function() {
            var a8;
            var a7;
            this.getEl();
            if (this.top0 === null) {
                this.sourceEl.width();
                a8 = this.sourceEl.offset();
                a7 = this.el.offsetParent().offset();
                this.top0 = a8.top - a7.top;
                this.left0 = a8.left - a7.left
            }
            this.el.css({
                top: this.top0 + this.topDelta,
                left: this.left0 + this.leftDelta
            })
        },
        mousemove: function(a7) {
            this.topDelta = a7.pageY - this.mouseY0;
            this.leftDelta = a7.pageX - this.mouseX0;
            if (!this.isHidden) {
                this.updatePosition()
            }
        },
        hide: function() {
            if (!this.isHidden) {
                this.isHidden = true;
                if (this.el) {
                    this.el.hide()
                }
            }
        },
        show: function() {
            if (this.isHidden) {
                this.isHidden = false;
                this.updatePosition();
                this.getEl().show()
            }
        }
    });
    var X = aR.extend({
        view: null,
        isRTL: null,
        cellHtml: "<td/>",
        constructor: function(a7) {
            this.view = a7;
            this.isRTL = a7.opt("isRTL")
        },
        rowHtml: function(bb, bc) {
            var a8 = this.getHtmlRenderer("cell", bb);
            var ba = "";
            var a9;
            var a7;
            bc = bc || 0;
            for (a9 = 0; a9 < this.colCnt; a9++) {
                a7 = this.getCell(bc, a9);
                ba += a8(a7)
            }
            ba = this.bookendCells(ba, bb, bc);
            return "<tr>" + ba + "</tr>"
        },
        bookendCells: function(a9, ba, bc) {
            var bb = this.getHtmlRenderer("intro", ba)(bc || 0);
            var a7 = this.getHtmlRenderer("outro", ba)(bc || 0);
            var bd = this.isRTL ? a7 : bb;
            var a8 = this.isRTL ? bb : a7;
            if (typeof a9 === "string") {
                return bd + a9 + a8
            } else {
                return a9.prepend(bd).append(a8)
            }
        },
        getHtmlRenderer: function(a8, a9) {
            var a7 = this.view;
            var bd;
            var ba;
            var bc;
            var bb;
            bd = a8 + "Html";
            if (a9) {
                ba = a9 + aQ(a8) + "Html"
            }
            if (ba && (bb = a7[ba])) {
                bc = a7
            } else {
                if (ba && (bb = this[ba])) {
                    bc = this
                } else {
                    if ((bb = a7[bd])) {
                        bc = a7
                    } else {
                        if ((bb = this[bd])) {
                            bc = this
                        }
                    }
                }
            }
            if (typeof bb === "function") {
                return function() {
                    return bb.apply(bc, arguments) || ""
                }
            }
            return function() {
                return bb || ""
            }
        }
    });
    var az = aY.Grid = X.extend({
        start: null,
        end: null,
        rowCnt: 0,
        colCnt: 0,
        rowData: null,
        colData: null,
        el: null,
        coordMap: null,
        elsByFill: null,
        documentDragStartProxy: null,
        colHeadFormat: null,
        eventTimeFormat: null,
        displayEventEnd: null,
        constructor: function() {
            X.apply(this, arguments);
            this.coordMap = new ah(this);
            this.elsByFill = {};
            this.documentDragStartProxy = aK.proxy(this, "documentDragStart")
        },
        render: function() {
            this.bindHandlers()
        },
        destroy: function() {
            this.unbindHandlers()
        },
        computeColHeadFormat: function() {},
        computeEventTimeFormat: function() {
            return this.view.opt("smallTimeFormat")
        },
        computeDisplayEventEnd: function() {
            return false
        },
        setRange: function(a8) {
            var a7 = this.view;
            this.start = a8.start.clone();
            this.end = a8.end.clone();
            this.rowData = [];
            this.colData = [];
            this.updateCells();
            this.colHeadFormat = a7.opt("columnFormat") || this.computeColHeadFormat();
            this.eventTimeFormat = a7.opt("timeFormat") || this.computeEventTimeFormat();
            this.displayEventEnd = a7.opt("displayEventEnd");
            if (this.displayEventEnd == null) {
                this.displayEventEnd = this.computeDisplayEventEnd()
            }
        },
        updateCells: function() {},
        rangeToSegs: function(a7) {},
        getCell: function(a9, a8) {
            var a7;
            if (a8 == null) {
                if (typeof a9 === "number") {
                    a8 = a9 % this.colCnt;
                    a9 = Math.floor(a9 / this.colCnt)
                } else {
                    a8 = a9.col;
                    a9 = a9.row
                }
            }
            a7 = {
                row: a9,
                col: a8
            };
            aK.extend(a7, this.getRowData(a9), this.getColData(a8));
            aK.extend(a7, this.computeCellRange(a7));
            return a7
        },
        computeCellRange: function(a7) {},
        getRowData: function(a7) {
            return this.rowData[a7] || {}
        },
        getColData: function(a7) {
            return this.colData[a7] || {}
        },
        getRowEl: function(a7) {},
        getColEl: function(a7) {},
        getCellDayEl: function(a7) {
            return this.getColEl(a7.col) || this.getRowEl(a7.row)
        },
        computeRowCoords: function() {
            var a7 = [];
            var a8, a9;
            var ba;
            for (a8 = 0; a8 < this.rowCnt; a8++) {
                a9 = this.getRowEl(a8);
                ba = {
                    top: a9.offset().top
                };
                if (a8 > 0) {
                    a7[a8 - 1].bottom = ba.top
                }
                a7.push(ba)
            }
            ba.bottom = ba.top + a9.outerHeight();
            return a7
        },
        computeColCoords: function() {
            var a7 = [];
            var a8, a9;
            var ba;
            for (a8 = 0; a8 < this.colCnt; a8++) {
                a9 = this.getColEl(a8);
                ba = {
                    left: a9.offset().left
                };
                if (a8 > 0) {
                    a7[a8 - 1].right = ba.left
                }
                a7.push(ba)
            }
            ba.right = ba.left + a9.outerWidth();
            return a7
        },
        bindHandlers: function() {
            var a7 = this;
            this.el.on("mousedown", function(a8) {
                if (!aK(a8.target).is(".fc-event-container *, .fc-more") && !aK(a8.target).closest(".fc-popover").length) {
                    a7.dayMousedown(a8)
                }
            });
            this.bindSegHandlers();
            aK(document).on("dragstart", this.documentDragStartProxy)
        },
        unbindHandlers: function() {
            aK(document).off("dragstart", this.documentDragStartProxy)
        },
        dayMousedown: function(bb) {
            var bd = this;
            var a9 = this.view;
            var a7 = a9.opt("selectable");
            var a8;
            var bc;
            var ba = new aP(this.coordMap, {
                scroll: a9.opt("dragScroll"),
                dragStart: function() {
                    a9.unselect()
                },
                cellOver: function(bg, bf) {
                    var be = ba.origCell;
                    if (be) {
                        a8 = bf ? bg : null;
                        if (a7) {
                            bc = bd.computeSelection(be, bg);
                            if (bc) {
                                bd.renderSelection(bc)
                            } else {
                                ar()
                            }
                        }
                    }
                },
                cellOut: function(be) {
                    a8 = null;
                    bc = null;
                    bd.destroySelection();
                    A()
                },
                listenStop: function(be) {
                    if (a8) {
                        a9.trigger("dayClick", bd.getCellDayEl(a8), a8.start, be)
                    }
                    if (bc) {
                        a9.reportSelection(bc, be)
                    }
                    A()
                }
            });
            ba.mousedown(bb)
        },
        renderRangeHelper: function(a8, a7) {
            var a9;
            a9 = a7 ? R(a7.event) : {};
            a9.start = a8.start.clone();
            a9.end = a8.end ? a8.end.clone() : null;
            a9.allDay = null;
            this.view.calendar.normalizeEventDateProps(a9);
            a9.className = (a9.className || []).concat("fc-helper");
            if (!a7) {
                a9.editable = false
            }
            this.renderHelper(a9, a7)
        },
        renderHelper: function(a8, a7) {},
        destroyHelper: function() {},
        renderSelection: function(a7) {
            this.renderHighlight(a7)
        },
        destroySelection: function() {
            this.destroyHighlight()
        },
        computeSelection: function(a9, a7) {
            var ba = [a9.start, a9.end, a7.start, a7.end];
            var a8;
            ba.sort(aj);
            a8 = {
                start: ba[0].clone(),
                end: ba[3].clone()
            };
            if (!this.view.calendar.isSelectionRangeAllowed(a8)) {
                return null
            }
            return a8
        },
        renderHighlight: function(a7) {
            this.renderFill("highlight", this.rangeToSegs(a7))
        },
        destroyHighlight: function() {
            this.destroyFill("highlight")
        },
        highlightSegClasses: function() {
            return ["fc-highlight"]
        },
        renderFill: function(a8, a7) {},
        destroyFill: function(a8) {
            var a7 = this.elsByFill[a8];
            if (a7) {
                a7.remove();
                delete this.elsByFill[a8]
            }
        },
        renderFillSegEls: function(bc, a8) {
            var bd = this;
            var a7 = this[bc + "SegEl"];
            var bb = "";
            var ba = [];
            var a9;
            if (a8.length) {
                for (a9 = 0; a9 < a8.length; a9++) {
                    bb += this.fillSegHtml(bc, a8[a9])
                }
                aK(bb).each(function(bf, bh) {
                    var be = a8[bf];
                    var bg = aK(bh);
                    if (a7) {
                        bg = a7.call(bd, be, bg)
                    }
                    if (bg) {
                        bg = aK(bg);
                        if (bg.is(bd.fillSegTag)) {
                            be.el = bg;
                            ba.push(be)
                        }
                    }
                })
            }
            return ba
        },
        fillSegTag: "div",
        fillSegHtml: function(ba, a7) {
            var bc = this[ba + "SegClasses"];
            var a8 = this[ba + "SegStyles"];
            var a9 = bc ? bc.call(this, a7) : [];
            var bb = a8 ? a8.call(this, a7) : "";
            return "<" + this.fillSegTag + (a9.length ? ' class="' + a9.join(" ") + '"' : "") + (bb ? ' style="' + bb + '"' : "") + " />"
        },
        headHtml: function() {
            return '<div class="fc-row ' + this.view.widgetHeaderClass + '"><table><thead>' + this.rowHtml("head") + "</thead></table></div>"
        },
        headCellHtml: function(a7) {
            var a8 = this.view;
            var a9 = a7.start;
            return '<th class="fc-day-header ' + a8.widgetHeaderClass + " fc-" + aa[a9.day()] + '">' + a2(a9.format(this.colHeadFormat)) + "</th>"
        },
        bgCellHtml: function(a7) {
            var a8 = this.view;
            var a9 = a7.start;
            var ba = this.getDayClasses(a9);
            ba.unshift("fc-day", a8.widgetContentClass);
            return '<td class="' + ba.join(" ") + '" data-date="' + a9.format("YYYY-MM-DD") + '"></td>'
        },
        getDayClasses: function(a9) {
            var a7 = this.view;
            var a8 = a7.calendar.getNow().stripTime();
            var ba = ["fc-" + aa[a9.day()]];
            if (a7.name === "month" && a9.month() != a7.intervalStart.month()) {
                ba.push("fc-other-month")
            }

            if (isFeriado(a9._d)) {
            	ba.push("fc-holiday");            	
            }
            else {
                if (a9.isSame(a8, "day")) {
                    ba.push("fc-today", a7.highlightStateClass)
                } else {
                    if (a9 < a8) {
                        ba.push("fc-past")
                    } else {
                        ba.push("fc-future")
                    }
                }            	
            }
            return ba
        }
    });
    az.mixin({
        mousedOverSeg: null,
        isDraggingSeg: false,
        isResizingSeg: false,
        segs: null,
        renderEvents: function(bb) {
            var a8 = this.eventsToSegs(bb);
            var a9 = [];
            var bc = [];
            var ba, a7;
            for (ba = 0; ba < a8.length; ba++) {
                a7 = a8[ba];
                if (aq(a7.event)) {
                    a9.push(a7)
                } else {
                    bc.push(a7)
                }
            }
            a9 = this.renderBgSegs(a9) || a9;
            bc = this.renderFgSegs(bc) || bc;
            this.segs = a9.concat(bc)
        },
        destroyEvents: function() {
            this.triggerSegMouseout();
            this.destroyFgSegs();
            this.destroyBgSegs();
            this.segs = null
        },
        getEventSegs: function() {
            return this.segs || []
        },
        renderFgSegs: function(a7) {},
        destroyFgSegs: function() {},
        renderFgSegEls: function(a9, a8) {
            var a7 = this.view;
            var bc = "";
            var bb = [];
            var ba;
            if (a9.length) {
                for (ba = 0; ba < a9.length; ba++) {
                    bc += this.fgSegHtml(a9[ba], a8)
                }
                aK(bc).each(function(be, bg) {
                    var bd = a9[be];
                    var bf = a7.resolveEventEl(bd.event, aK(bg));
                    if (bf) {
                        bf.data("fc-seg", bd);
                        bd.el = bf;
                        bb.push(bd)
                    }
                })
            }
            return bb
        },
        fgSegHtml: function(a8, a7) {},
        renderBgSegs: function(a7) {
            return this.renderFill("bgEvent", a7)
        },
        destroyBgSegs: function() {
            this.destroyFill("bgEvent")
        },
        bgEventSegEl: function(a7, a8) {
            return this.view.resolveEventEl(a7.event, a8)
        },
        bgEventSegClasses: function(a7) {
            var a8 = a7.event;
            var a9 = a8.source || {};
            return ["fc-bgevent"].concat(a8.className, a9.className || [])
        },
        bgEventSegStyles: function(a8) {
            var a7 = this.view;
            var bc = a8.event;
            var bd = bc.source || {};
            var ba = bc.color;
            var be = bd.color;
            var bb = a7.opt("eventColor");
            var a9 = bc.backgroundColor || ba || bd.backgroundColor || be || a7.opt("eventBackgroundColor") || bb;
            if (a9) {
                return "background-color:" + a9
            }
            return ""
        },
        businessHoursSegClasses: function(a7) {
            return ["fc-nonbusiness", "fc-bgevent"]
        },
        bindSegHandlers: function() {
            var a8 = this;
            var a7 = this.view;
            aK.each({
                mouseenter: function(a9, ba) {
                    a8.triggerSegMouseover(a9, ba)
                },
                mouseleave: function(a9, ba) {
                    a8.triggerSegMouseout(a9, ba)
                },
                click: function(a9, ba) {
                    return a7.trigger("eventClick", this, a9.event, ba)
                },
                mousedown: function(a9, ba) {
                    if (aK(ba.target).is(".fc-resizer") && a7.isEventResizable(a9.event)) {
                        a8.segResizeMousedown(a9, ba)
                    } else {
                        if (a7.isEventDraggable(a9.event)) {
                            a8.segDragMousedown(a9, ba)
                        }
                    }
                }
            }, function(a9, ba) {
                a8.el.on(a9, ".fc-event-container > *", function(bc) {
                    var bb = aK(this).data("fc-seg");
                    if (bb && !a8.isDraggingSeg && !a8.isResizingSeg) {
                        return ba.call(this, bb, bc)
                    }
                })
            })
        },
        triggerSegMouseover: function(a7, a8) {
            if (!this.mousedOverSeg) {
                this.mousedOverSeg = a7;
                this.view.trigger("eventMouseover", a7.el[0], a7.event, a8)
            }
        },
        triggerSegMouseout: function(a7, a8) {
            a8 = a8 || {};
            if (this.mousedOverSeg) {
                a7 = a7 || this.mousedOverSeg;
                this.mousedOverSeg = null;
                this.view.trigger("eventMouseout", a7.el[0], a7.event, a8)
            }
        },
        segDragMousedown: function(bb, be) {
            var bd = this;
            var bf = this.view;
            var ba = bb.el;
            var a9 = bb.event;
            var bc;
            var a8 = new C(bb.el, {
                parentEl: bf.el,
                opacity: bf.opt("dragOpacity"),
                revertDuration: bf.opt("dragRevertDuration"),
                zIndex: 2
            });
            var a7 = new aP(bf.coordMap, {
                distance: 5,
                scroll: bf.opt("dragScroll"),
                listenStart: function(bg) {
                    a8.hide();
                    a8.start(bg)
                },
                dragStart: function(bg) {
                    bd.triggerSegMouseout(bb, bg);
                    bd.isDraggingSeg = true;
                    bf.hideEvent(a9);
                    bf.trigger("eventDragStart", ba[0], a9, bg, {})
                },
                cellOver: function(bi, bh) {
                    var bg = bb.cell || a7.origCell;
                    bc = bd.computeEventDrop(bg, bi, a9);
                    if (bc) {
                        if (bf.renderDrag(bc, bb)) {
                            a8.hide()
                        } else {
                            a8.show()
                        }
                        if (bh) {
                            bc = null
                        }
                    } else {
                        a8.show();
                        ar()
                    }
                },
                cellOut: function() {
                    bc = null;
                    bf.destroyDrag();
                    a8.show();
                    A()
                },
                dragStop: function(bg) {
                    a8.stop(!bc, function() {
                        bd.isDraggingSeg = false;
                        bf.destroyDrag();
                        bf.showEvent(a9);
                        bf.trigger("eventDragStop", ba[0], a9, bg, {});
                        if (bc) {
                            bf.reportEventDrop(a9, bc, ba, bg)
                        }
                    });
                    A()
                },
                listenStop: function() {
                    a8.stop()
                }
            });
            a7.mousedown(be)
        },
        computeEventDrop: function(be, a9, a7) {
            var bf = be.start;
            var ba = a9.start;
            var bg;
            var bc;
            var bb;
            var a8;
            var bd;
            if (bf.hasTime() === ba.hasTime()) {
                bg = t(ba, bf);
                bc = a7.start.clone().add(bg);
                if (a7.end === null) {
                    bb = null
                } else {
                    bb = a7.end.clone().add(bg)
                }
                a8 = a7.allDay
            } else {
                bc = ba.clone();
                bb = null;
                a8 = !ba.hasTime()
            }
            bd = {
                start: bc,
                end: bb,
                allDay: a8
            };
            if (!this.view.calendar.isEventRangeAllowed(bd, a7)) {
                return null
            }
            return bd
        },
        documentDragStart: function(ba, bb) {
            var a7 = this.view;
            var a9;
            var a8;
            if (a7.opt("droppable")) {
                a9 = aK(ba.target);
                a8 = a7.opt("dropAccept");
                if (aK.isFunction(a8) ? a8.call(a9[0], a9) : a9.is(a8)) {
                    this.startExternalDrag(a9, ba, bb)
                }
            }
        },
        startExternalDrag: function(a8, ba, bb) {
            var bd = this;
            var bc = ai(a8);
            var a7;
            var a9;
            a7 = new aP(this.coordMap, {
                cellOver: function(be) {
                    a9 = bd.computeExternalDrop(be, bc);
                    if (a9) {
                        bd.renderDrag(a9)
                    } else {
                        ar()
                    }
                },
                cellOut: function() {
                    a9 = null;
                    bd.destroyDrag();
                    A()
                }
            });
            aK(document).one("dragstop", function(be, bf) {
                bd.destroyDrag();
                A();
                if (a9) {
                    bd.view.reportExternalDrop(bc, a9, a8, be, bf)
                }
            });
            a7.startDrag(ba)
        },
        computeExternalDrop: function(a7, a9) {
            var a8 = {
                start: a7.start.clone(),
                end: null
            };
            if (a9.startTime && !a8.start.hasTime()) {
                a8.start.time(a9.startTime)
            }
            if (a9.duration) {
                a8.end = a8.start.clone().add(a9.duration)
            }
            if (!this.view.calendar.isExternalDropRangeAllowed(a8, a9.eventProps)) {
                return null
            }
            return a8
        },
        renderDrag: function(a8, a7) {},
        destroyDrag: function() {},
        segResizeMousedown: function(bc, bf) {
            var be = this;
            var bg = this.view;
            var bd = bg.calendar;
            var ba = bc.el;
            var a8 = bc.event;
            var a9 = a8.start;
            var bi = bd.getEventEnd(a8);
            var bb;
            var a7;

            function bh() {
                be.destroyEventResize();
                bg.showEvent(a8);
                A()
            }
            a7 = new aP(this.coordMap, {
                distance: 5,
                scroll: bg.opt("dragScroll"),
                dragStart: function(bj) {
                    be.triggerSegMouseout(bc, bj);
                    be.isResizingSeg = true;
                    bg.trigger("eventResizeStart", ba[0], a8, bj, {})
                },
                cellOver: function(bj) {
                    bb = bj.end;
                    if (!bb.isAfter(a9)) {
                        bb = a9.clone().add(t(bj.end, bj.start))
                    }
                    if (bb.isSame(bi)) {
                        bb = null
                    } else {
                        if (!bd.isEventRangeAllowed({
                                start: a9,
                                end: bb
                            }, a8)) {
                            bb = null;
                            ar()
                        } else {
                            be.renderEventResize({
                                start: a9,
                                end: bb
                            }, bc);
                            bg.hideEvent(a8)
                        }
                    }
                },
                cellOut: function() {
                    bb = null;
                    bh()
                },
                dragStop: function(bj) {
                    be.isResizingSeg = false;
                    bh();
                    bg.trigger("eventResizeStop", ba[0], a8, bj, {});
                    if (bb) {
                        bg.reportEventResize(a8, bb, ba, bj)
                    }
                }
            });
            a7.mousedown(bf)
        },
        renderEventResize: function(a8, a7) {},
        destroyEventResize: function() {},
        getEventTimeText: function(a8, a7) {
            a7 = a7 || this.eventTimeFormat;
            if (a8.end && this.displayEventEnd) {
                return this.view.formatRange(a8, a7)
            } else {
                return a8.start.format(a7)
            }
        },
        getSegClasses: function(a7, a9, bb) {
            var ba = a7.event;
            var a8 = ["fc-event", a7.isStart ? "fc-start" : "fc-not-start", a7.isEnd ? "fc-end" : "fc-not-end"].concat(ba.className, ba.source ? ba.source.className : []);
            if (a9) {
                a8.push("fc-draggable")
            }
            if (bb) {
                a8.push("fc-resizable")
            }
            return a8
        },
        getEventSkinCss: function(a8) {
            var bd = this.view;
            var a7 = a8.source || {};
            var be = a8.color;
            var bb = a7.color;
            var ba = bd.opt("eventColor");
            var bf = a8.backgroundColor || be || a7.backgroundColor || bb || bd.opt("eventBackgroundColor") || ba;
            var a9 = a8.borderColor || be || a7.borderColor || bb || bd.opt("eventBorderColor") || ba;
            var bg = a8.textColor || a7.textColor || bd.opt("eventTextColor");
            var bc = [];
            if (bf) {
                bc.push("background-color:" + bf)
            }
            if (a9) {
                bc.push("border-color:" + a9)
            }
            if (bg) {
                bc.push("color:" + bg)
            }
            return bc.join(";")
        },
        eventsToSegs: function(bb, a9) {
            var a8 = this.eventsToRanges(bb);
            var a7 = [];
            var ba;
            for (ba = 0; ba < a8.length; ba++) {
                a7.push.apply(a7, this.eventRangeToSegs(a8[ba], a9))
            }
            return a7
        },
        eventsToRanges: function(a8) {
            var ba = this;
            var a9 = aD(a8);
            var a7 = [];
            aK.each(a9, function(bc, bb) {
                if (bb.length) {
                    a7.push.apply(a7, b(bb[0]) ? ba.eventsToInverseRanges(bb) : ba.eventsToNormalRanges(bb))
                }
            });
            return a7
        },
        eventsToNormalRanges: function(bb) {
            var bd = this.view.calendar;
            var a9 = [];
            var ba, bc;
            var a8, a7;
            for (ba = 0; ba < bb.length; ba++) {
                bc = bb[ba];
                a8 = bc.start.clone().stripZone();
                a7 = bd.getEventEnd(bc).stripZone();
                a9.push({
                    event: bc,
                    start: a8,
                    end: a7,
                    eventStartMS: +a8,
                    eventDurationMS: a7 - a8
                })
            }
            return a9
        },
        eventsToInverseRanges: function(bg) {
            var bc = this.view;
            var bb = bc.start.clone().stripZone();
            var bf = bc.end.clone().stripZone();
            var bd = this.eventsToNormalRanges(bg);
            var a8 = [];
            var be = bg[0];
            var a7 = bb;
            var a9, ba;
            bd.sort(aN);
            for (a9 = 0; a9 < bd.length; a9++) {
                ba = bd[a9];
                if (ba.start > a7) {
                    a8.push({
                        event: be,
                        start: a7,
                        end: ba.start
                    })
                }
                a7 = ba.end
            }
            if (a7 < bf) {
                a8.push({
                    event: be,
                    start: a7,
                    end: bf
                })
            }
            return a8
        },
        eventRangeToSegs: function(bb, a9) {
            var a8;
            var ba, a7;
            if (a9) {
                a8 = a9(bb)
            } else {
                a8 = this.rangeToSegs(bb)
            }
            for (ba = 0; ba < a8.length; ba++) {
                a7 = a8[ba];
                a7.event = bb.event;
                a7.eventStartMS = bb.eventStartMS;
                a7.eventDurationMS = bb.eventDurationMS
            }
            return a8
        }
    });

    function aq(a8) {
        var a7 = an(a8);
        return a7 === "background" || a7 === "inverse-background"
    }

    function b(a7) {
        return an(a7) === "inverse-background"
    }

    function an(a7) {
        return a4((a7.source || {}).rendering, a7.rendering)
    }

    function aD(a8) {
        var a9 = {};
        var a7, ba;
        for (a7 = 0; a7 < a8.length; a7++) {
            ba = a8[a7];
            (a9[ba._id] || (a9[ba._id] = [])).push(ba)
        }
        return a9
    }

    function aN(a8, a7) {
        return a8.eventStartMS - a7.eventStartMS
    }

    function y(a8, a7) {
        return a8.eventStartMS - a7.eventStartMS || a7.eventDurationMS - a8.eventDurationMS || a7.event.allDay - a8.event.allDay || (a8.event.title || "").localeCompare(a7.event.title)
    }
    aY.compareSegs = y;
    aY.dataAttrPrefix = "";

    function ai(a9) {
        var bb = aY.dataAttrPrefix;
        var ba;
        var a8;
        var bc;
        var a7;
        if (bb) {
            bb += "-"
        }
        ba = a9.data(bb + "event") || null;
        if (ba) {
            if (typeof ba === "object") {
                ba = aK.extend({}, ba)
            } else {
                ba = {}
            }
            a8 = ba.start;
            if (a8 == null) {
                a8 = ba.time
            }
            bc = ba.duration;
            a7 = ba.stick;
            delete ba.start;
            delete ba.time;
            delete ba.duration;
            delete ba.stick
        }
        if (a8 == null) {
            a8 = a9.data(bb + "start")
        }
        if (a8 == null) {
            a8 = a9.data(bb + "time")
        }
        if (bc == null) {
            bc = a9.data(bb + "duration")
        }
        if (a7 == null) {
            a7 = a9.data(bb + "stick")
        }
        a8 = a8 != null ? z.duration(a8) : null;
        bc = bc != null ? z.duration(bc) : null;
        a7 = Boolean(a7);
        return {
            eventProps: ba,
            startTime: a8,
            duration: bc,
            stick: a7
        }
    }
    var aS = az.extend({
        numbersVisible: false,
        bottomCoordPadding: 0,
        breakOnWeeks: null,
        cellDates: null,
        dayToCellOffsets: null,
        rowEls: null,
        dayEls: null,
        helperEls: null,
        render: function(a7) {
            var bc = this.view;
            var bb = this.rowCnt;
            var bd = this.colCnt;
            var a8 = bb * bd;
            var ba = "";
            var bf;
            var a9, be;
            for (bf = 0; bf < bb; bf++) {
                ba += this.dayRowHtml(bf, a7)
            }
            this.el.html(ba);
            this.rowEls = this.el.find(".fc-row");
            this.dayEls = this.el.find(".fc-day");
            for (a9 = 0; a9 < a8; a9++) {
                be = this.getCell(a9);
                bc.trigger("dayRender", null, be.start, this.dayEls.eq(a9))
            }
            az.prototype.render.call(this)
        },
        destroy: function() {
            this.destroySegPopover();
            az.prototype.destroy.call(this)
        },
        dayRowHtml: function(ba, a8) {
            var a7 = this.view;
            var a9 = ["fc-row", "fc-week", a7.widgetContentClass];
            if (a8) {
                a9.push("fc-rigid")
            }
            return '<div class="' + a9.join(" ") + '"><div class="fc-bg"><table>' + this.rowHtml("day", ba) + '</table></div><div class="fc-content-skeleton"><table>' + (this.numbersVisible ? "<thead>" + this.rowHtml("number", ba) + "</thead>" : "") + "</table></div></div>"
        },
        dayCellHtml: function(a7) {
            return this.bgCellHtml(a7)
        },
        computeColHeadFormat: function() {
            if (this.rowCnt > 1) {
                return "ddd"
            } else {
                if (this.colCnt > 1) {
                    return this.view.opt("dayOfMonthFormat")
                } else {
                    return "dddd"
                }
            }
        },
        computeEventTimeFormat: function() {
            return this.view.opt("extraSmallTimeFormat")
        },
        computeDisplayEventEnd: function() {
            return this.colCnt == 1
        },
        updateCells: function() {
            var a9;
            var a7;
            var ba;
            var a8;
            this.updateCellDates();
            a9 = this.cellDates;
            if (this.breakOnWeeks) {
                a7 = a9[0].day();
                for (a8 = 1; a8 < a9.length; a8++) {
                    if (a9[a8].day() == a7) {
                        break
                    }
                }
                ba = Math.ceil(a9.length / a8)
            } else {
                ba = 1;
                a8 = a9.length
            }
            this.rowCnt = ba;
            this.colCnt = a8
        },
        updateCellDates: function() {
            var a7 = this.view;
            var a8 = this.start.clone();
            var ba = [];
            var bb = -1;
            var a9 = [];
            while (a8.isBefore(this.end)) {
                if (a7.isHiddenDay(a8)) {
                    a9.push(bb + 0.5)
                } else {
                    bb++;
                    a9.push(bb);
                    ba.push(a8.clone())
                }
                a8.add(1, "days")
            }
            this.cellDates = ba;
            this.dayToCellOffsets = a9
        },
        computeCellRange: function(a7) {
            var ba = this.colCnt;
            var a9 = a7.row * ba + (this.isRTL ? ba - a7.col - 1 : a7.col);
            var bb = this.cellDates[a9].clone();
            var a8 = bb.clone().add(1, "day");
            return {
                start: bb,
                end: a8
            }
        },
        getRowEl: function(a7) {
            return this.rowEls.eq(a7)
        },
        getColEl: function(a7) {
            return this.dayEls.eq(a7)
        },
        getCellDayEl: function(a7) {
            return this.dayEls.eq(a7.row * this.colCnt + a7.col)
        },
        computeRowCoords: function() {
            var a7 = az.prototype.computeRowCoords.call(this);
            a7[a7.length - 1].bottom += this.bottomCoordPadding;
            return a7
        },
        rangeToSegs: function(bd) {
            var a9 = this.isRTL;
            var bf = this.rowCnt;
            var bi = this.colCnt;
            var a8 = [];
            var bc, bj;
            var bl;
            var bg, bh;
            var a7, ba;
            var bk, be;
            var bb;
            bd = this.view.computeDayRange(bd);
            bc = this.dateToCellOffset(bd.start);
            bj = this.dateToCellOffset(bd.end.subtract(1, "days"));
            for (bl = 0; bl < bf; bl++) {
                bg = bl * bi;
                bh = bg + bi - 1;
                bk = Math.max(bg, bc);
                be = Math.min(bh, bj);
                bk = Math.ceil(bk);
                be = Math.floor(be);
                if (bk <= be) {
                    a7 = bk === bc;
                    ba = be === bj;
                    bk -= bg;
                    be -= bg;
                    bb = {
                        row: bl,
                        isStart: a7,
                        isEnd: ba
                    };
                    if (a9) {
                        bb.leftCol = bi - be - 1;
                        bb.rightCol = bi - bk - 1
                    } else {
                        bb.leftCol = bk;
                        bb.rightCol = be
                    }
                    a8.push(bb)
                }
            }
            return a8
        },
        dateToCellOffset: function(a8) {
            var a9 = this.dayToCellOffsets;
            var a7 = a8.diff(this.start, "days");
            if (a7 < 0) {
                return a9[0] - 1
            } else {
                if (a7 >= a9.length) {
                    return a9[a9.length - 1] + 1
                } else {
                    return a9[a7]
                }
            }
        },
        renderDrag: function(a9, a7) {
            var a8;
            this.renderHighlight(this.view.calendar.ensureVisibleEventRange(a9));
            if (a7 && !a7.el.closest(this.el).length) {
                this.renderRangeHelper(a9, a7);
                a8 = this.view.opt("dragOpacity");
                if (a8 !== undefined) {
                    this.helperEls.css("opacity", a8)
                }
                return true
            }
        },
        destroyDrag: function() {
            this.destroyHighlight();
            this.destroyHelper()
        },
        renderEventResize: function(a8, a7) {
            this.renderHighlight(a8);
            this.renderRangeHelper(a8, a7)
        },
        destroyEventResize: function() {
            this.destroyHighlight();
            this.destroyHelper()
        },
        renderHelper: function(bb, a9) {
            var a7 = [];
            var a8 = this.eventsToSegs([bb]);
            var ba;
            a8 = this.renderFgSegEls(a8);
            ba = this.renderSegRows(a8);
            this.rowEls.each(function(bg, be) {
                var bd = aK(be);
                var bf = aK('<div class="fc-helper-skeleton"><table/></div>');
                var bc;
                if (a9 && a9.row === bg) {
                    bc = a9.el.position().top
                } else {
                    bc = bd.find(".fc-content-skeleton tbody").position().top
                }
                bf.css("top", bc).find("table").append(ba[bg].tbodyEl);
                bd.append(bf);
                a7.push(bf[0])
            });
            this.helperEls = aK(a7)
        },
        destroyHelper: function() {
            if (this.helperEls) {
                this.helperEls.remove();
                this.helperEls = null
            }
        },
        fillSegTag: "td",
        renderFill: function(bb, a8) {
            var a9 = [];
            var ba, a7;
            var bc;
            a8 = this.renderFillSegEls(bb, a8);
            for (ba = 0; ba < a8.length; ba++) {
                a7 = a8[ba];
                bc = this.renderFillRow(bb, a7);
                this.rowEls.eq(a7.row).append(bc);
                a9.push(bc[0])
            }
            this.elsByFill[bb] = aK(a9);
            return a8
        },
        renderFillRow: function(a9, a7) {
            var a8 = this.colCnt;
            var bc = a7.leftCol;
            var bb = a7.rightCol + 1;
            var bd;
            var ba;
            bd = aK('<div class="fc-' + a9.toLowerCase() + '-skeleton"><table><tr/></table></div>');
            ba = bd.find("tr");
            if (bc > 0) {
                ba.append('<td colspan="' + bc + '"/>')
            }
            ba.append(a7.el.attr("colspan", bb - bc));
            if (bb < a8) {
                ba.append('<td colspan="' + (a8 - bb) + '"/>')
            }
            this.bookendCells(ba, a9);
            return bd
        }
    });
    aS.mixin({
        rowStructs: null,
        destroyEvents: function() {
            this.destroySegPopover();
            az.prototype.destroyEvents.apply(this, arguments)
        },
        getEventSegs: function() {
            return az.prototype.getEventSegs.call(this).concat(this.popoverSegs || [])
        },
        renderBgSegs: function(a7) {
            var a8 = aK.grep(a7, function(a9) {
                return a9.event.allDay
            });
            return az.prototype.renderBgSegs.call(this, a8)
        },
        renderFgSegs: function(a7) {
            var a8;
            a7 = this.renderFgSegEls(a7);
            a8 = this.rowStructs = this.renderSegRows(a7);
            this.rowEls.each(function(a9, ba) {
                aK(ba).find(".fc-content-skeleton > table").append(a8[a9].tbodyEl)
            });
            return a7
        },
        destroyFgSegs: function() {
            var a8 = this.rowStructs || [];
            var a7;
            while ((a7 = a8.pop())) {
                a7.tbodyEl.remove()
            }
            this.rowStructs = null
        },
        renderSegRows: function(a8) {
            var a9 = [];
            var a7;
            var ba;
            a7 = this.groupSegRows(a8);
            for (ba = 0; ba < a7.length; ba++) {
                a9.push(this.renderSegRow(ba, a7[ba]))
            }
            return a9
        },
        fgSegHtml: function(bc, a9) {
            var bf = this.view;
            var a8 = bc.event;
            var a7 = bf.isEventDraggable(a8);
            var ba = !a9 && a8.allDay && bc.isEnd && bf.isEventResizable(a8);
            var bb = this.getSegClasses(bc, a7, ba);
            var bg = this.getEventSkinCss(a8);
            var be = "";
            var bd;
            bb.unshift("fc-day-grid-event");
            if (!a8.allDay && bc.isStart) {
                be = '<span class="fc-time">' + a2(this.getEventTimeText(a8)) + "</span>"
            }
            bd = '<span class="fc-title">' + (a2(a8.title || "") || "&nbsp;") + "</span>";
            return '<a class="' + bb.join(" ") + '"' + (a8.url ? ' href="' + a2(a8.url) + '"' : "") + (bg ? ' style="' + bg + '"' : "") + '><div class="fc-content">' + (this.isRTL ? bd + " " + be : be + " " + bd) + "</div>" + (ba ? '<div class="fc-resizer"/>' : "") + "</a>"
        },
        renderSegRow: function(bm, bn) {
            var bk = this.colCnt;
            var bg = this.buildSegLevels(bn);
            var a7 = Math.max(1, bg.length);
            var bf = aK("<tbody/>");
            var bb = [];
            var bh = [];
            var a9 = [];
            var be, bj;
            var a8;
            var bi;
            var bd, bc;
            var ba;

            function bl(bo) {
                while (a8 < bo) {
                    ba = (a9[be - 1] || [])[a8];
                    if (ba) {
                        ba.attr("rowspan", parseInt(ba.attr("rowspan") || 1, 10) + 1)
                    } else {
                        ba = aK("<td/>");
                        bi.append(ba)
                    }
                    bh[be][a8] = ba;
                    a9[be][a8] = ba;
                    a8++
                }
            }
            for (be = 0; be < a7; be++) {
                bj = bg[be];
                a8 = 0;
                bi = aK("<tr/>");
                bb.push([]);
                bh.push([]);
                a9.push([]);
                if (bj) {
                    for (bd = 0; bd < bj.length; bd++) {
                        bc = bj[bd];
                        bl(bc.leftCol);
                        ba = aK('<td class="fc-event-container"/>').append(bc.el);
                        if (bc.leftCol != bc.rightCol) {
                            ba.attr("colspan", bc.rightCol - bc.leftCol + 1)
                        } else {
                            a9[be][a8] = ba
                        }
                        while (a8 <= bc.rightCol) {
                            bh[be][a8] = ba;
                            bb[be][a8] = bc;
                            a8++
                        }
                        bi.append(ba)
                    }
                }
                bl(bk);
                this.bookendCells(bi, "eventSkeleton");
                bf.append(bi)
            }
            return {
                row: bm,
                tbodyEl: bf,
                cellMatrix: bh,
                segMatrix: bb,
                segLevels: bg,
                segs: bn
            }
        },
        buildSegLevels: function(a8) {
            var bb = [];
            var ba, a7;
            var a9;
            a8.sort(y);
            for (ba = 0; ba < a8.length; ba++) {
                a7 = a8[ba];
                for (a9 = 0; a9 < bb.length; a9++) {
                    if (!B(a7, bb[a9])) {
                        break
                    }
                }
                a7.level = a9;
                (bb[a9] || (bb[a9] = [])).push(a7)
            }
            for (a9 = 0; a9 < bb.length; a9++) {
                bb[a9].sort(ay)
            }
            return bb
        },
        groupSegRows: function(a8) {
            var a7 = [];
            var a9;
            for (a9 = 0; a9 < this.rowCnt; a9++) {
                a7.push([])
            }
            for (a9 = 0; a9 < a8.length; a9++) {
                a7[a8[a9].row].push(a8[a9])
            }
            return a7
        }
    });

    function B(a7, ba) {
        var a8, a9;
        for (a8 = 0; a8 < ba.length; a8++) {
            a9 = ba[a8];
            if (a9.leftCol <= a7.rightCol && a9.rightCol >= a7.leftCol) {
                return true
            }
        }
        return false
    }

    function ay(a8, a7) {
        return a8.leftCol - a7.leftCol
    }
    aS.mixin({
        segPopover: null,
        popoverSegs: null,
        destroySegPopover: function() {
            if (this.segPopover) {
                this.segPopover.hide()
            }
        },
        limitRows: function(a9) {
            var a7 = this.rowStructs || [];
            var ba;
            var a8;
            for (ba = 0; ba < a7.length; ba++) {
                this.unlimitRow(ba);
                if (!a9) {
                    a8 = false
                } else {
                    if (typeof a9 === "number") {
                        a8 = a9
                    } else {
                        a8 = this.computeRowLevelLimit(ba)
                    }
                }
                if (a8 !== false) {
                    this.limitRow(ba, a8)
                }
            }
        },
        computeRowLevelLimit: function(bc) {
            var a8 = this.rowEls.eq(bc);
            var bb = a8.height();
            var a7 = this.rowStructs[bc].tbodyEl.children();
            var a9, ba;
            for (a9 = 0; a9 < a7.length; a9++) {
                ba = a7.eq(a9).removeClass("fc-limited");
                if (ba.position().top + ba.outerHeight() > bb) {
                    return a9
                }
            }
            return false
        },
        limitRow: function(bg, bk) {
            var bm = this;
            var ba = this.rowStructs[bg];
            var bf = [];
            var be = 0;
            var a7;
            var a8;
            var bd;
            var br;
            var bp, bq;
            var bt;
            var bh;
            var a9;
            var bi, bs;
            var bc;
            var bn;
            var bb, bo, bl;

            function bj(bu) {
                while (be < bu) {
                    a7 = bm.getCell(bg, be);
                    bt = bm.getCellSegs(a7, bk);
                    if (bt.length) {
                        bi = bd[bk - 1][be];
                        bl = bm.renderMoreLink(a7, bt);
                        bo = aK("<div/>").append(bl);
                        bi.append(bo);
                        bf.push(bo[0])
                    }
                    be++
                }
            }
            if (bk && bk < ba.segLevels.length) {
                a8 = ba.segLevels[bk - 1];
                bd = ba.cellMatrix;
                br = ba.tbodyEl.children().slice(bk).addClass("fc-limited").get();
                for (bp = 0; bp < a8.length; bp++) {
                    bq = a8[bp];
                    bj(bq.leftCol);
                    a9 = [];
                    bh = 0;
                    while (be <= bq.rightCol) {
                        a7 = this.getCell(bg, be);
                        bt = this.getCellSegs(a7, bk);
                        a9.push(bt);
                        bh += bt.length;
                        be++
                    }
                    if (bh) {
                        bi = bd[bk - 1][bq.leftCol];
                        bs = bi.attr("rowspan") || 1;
                        bc = [];
                        for (bn = 0; bn < a9.length; bn++) {
                            bb = aK('<td class="fc-more-cell"/>').attr("rowspan", bs);
                            bt = a9[bn];
                            a7 = this.getCell(bg, bq.leftCol + bn);
                            bl = this.renderMoreLink(a7, [bq].concat(bt));
                            bo = aK("<div/>").append(bl);
                            bb.append(bo);
                            bc.push(bb[0]);
                            bf.push(bb[0])
                        }
                        bi.addClass("fc-limited").after(aK(bc));
                        br.push(bi[0])
                    }
                }
                bj(this.colCnt);
                ba.moreEls = aK(bf);
                ba.limitedEls = aK(br)
            }
        },
        unlimitRow: function(a8) {
            var a7 = this.rowStructs[a8];
            if (a7.moreEls) {
                a7.moreEls.remove();
                a7.moreEls = null
            }
            if (a7.limitedEls) {
                a7.limitedEls.removeClass("fc-limited");
                a7.limitedEls = null
            }
        },
        renderMoreLink: function(a7, a9) {
            var ba = this;
            var a8 = this.view;
            return aK('<a class="fc-more"/>').text(this.getMoreLinkText(a9.length)).on("click", function(bf) {
                var bi = a8.opt("eventLimitClick");
                var bc = a7.start;
                var bg = aK(this);
                var bh = ba.getCellDayEl(a7);
                var bb = ba.getCellSegs(a7);
                var bd = ba.resliceDaySegs(bb, bc);
                var be = ba.resliceDaySegs(a9, bc);
                if (typeof bi === "function") {
                    bi = a8.trigger("eventLimitClick", null, {
                        date: bc,
                        dayEl: bh,
                        moreEl: bg,
                        segs: bd,
                        hiddenSegs: be
                    }, bf)
                }
                if (bi === "popover") {
                    ba.showSegPopover(a7, bg, bd)
                } else {
                    if (typeof bi === "string") {
                        a8.calendar.zoomTo(bc, bi)
                    }
                }
            })
        },
        showSegPopover: function(a7, be, a9) {
            var bd = this;
            var a8 = this.view;
            var bb = be.parent();
            var bc;
            var ba;
            if (this.rowCnt == 1) {
                bc = a8.el
            } else {
                bc = this.rowEls.eq(a7.row)
            }
            ba = {
                className: "fc-more-popover",
                content: this.renderSegPopoverContent(a7, a9),
                parentEl: this.el,
                top: bc.offset().top,
                autoHide: true,
                viewportConstrain: a8.opt("popoverViewportConstrain"),
                hide: function() {
                    bd.segPopover.destroy();
                    bd.segPopover = null;
                    bd.popoverSegs = null
                }
            };
            if (this.isRTL) {
                ba.right = bb.offset().left + bb.outerWidth() + 1
            } else {
                ba.left = bb.offset().left - 1
            }
            this.segPopover = new k(ba);
            this.segPopover.show()
        },
        renderSegPopoverContent: function(a7, a9) {
            var a8 = this.view;
            var be = a8.opt("theme");
            var bd = a7.start.format(a8.opt("dayPopoverFormat"));
            var bc = aK('<div class="fc-header ' + a8.widgetHeaderClass + '"><span class="fc-close ' + (be ? "ui-icon ui-icon-closethick" : "fc-icon fc-icon-x") + '"></span><span class="fc-title">' + a2(bd) + '</span><div class="fc-clear"/></div><div class="fc-body ' + a8.widgetContentClass + '"><div class="fc-event-container"></div></div>');
            var bb = bc.find(".fc-event-container");
            var ba;
            a9 = this.renderFgSegEls(a9, true);
            this.popoverSegs = a9;
            for (ba = 0; ba < a9.length; ba++) {
                a9[ba].cell = a7;
                bb.append(a9[ba].el)
            }
            return bc
        },
        resliceDaySegs: function(a9, a7) {
            var bb = aK.map(a9, function(bd) {
                return bd.event
            });
            var a8 = a7.clone().stripTime();
            var bc = a8.clone().add(1, "days");
            var ba = {
                start: a8,
                end: bc
            };
            return this.eventsToSegs(bb, function(be) {
                var bd = aI(be, ba);
                return bd ? [bd] : []
            })
        },
        getMoreLinkText: function(a7) {
            var a8 = this.view.opt("eventLimitText");
            if (typeof a8 === "function") {
                return a8(a7)
            } else {
                return "+" + a7 + " " + a8
            }
        },
        getCellSegs: function(a7, ba) {
            var bb = this.rowStructs[a7.row].segMatrix;
            var bc = ba || 0;
            var a9 = [];
            var a8;
            while (bc < bb.length) {
                a8 = bb[bc][a7.col];
                if (a8) {
                    a9.push(a8)
                }
                bc++
            }
            return a9
        }
    });
    var h = az.extend({
        slotDuration: null,
        snapDuration: null,
        minTime: null,
        maxTime: null,
        axisFormat: null,
        dayEls: null,
        slatEls: null,
        slatTops: null,
        helperEl: null,
        businessHourSegs: null,
        constructor: function() {
            az.apply(this, arguments);
            this.processOptions()
        },
        render: function() {
            this.el.html(this.renderHtml());
            this.dayEls = this.el.find(".fc-day");
            this.slatEls = this.el.find(".fc-slats tr");
            this.computeSlatTops();
            this.renderBusinessHours();
            az.prototype.render.call(this)
        },
        renderBusinessHours: function() {
            var a7 = this.view.calendar.getBusinessHoursEvents();
            this.businessHourSegs = this.renderFill("businessHours", this.eventsToSegs(a7), "bgevent")
        },
        renderHtml: function() {
            return '<div class="fc-bg"><table>' + this.rowHtml("slotBg") + '</table></div><div class="fc-slats"><table>' + this.slatRowHtml() + "</table></div>"
        },
        slotBgCellHtml: function(a7) {
            return this.bgCellHtml(a7)
        },
        slatRowHtml: function() {
            var a7 = this.view;
            var bc = this.isRTL;
            var bb = "";
            var ba = this.slotDuration.asMinutes() % 15 === 0;
            var bd = z.duration(+this.minTime);
            var a8;
            var a9;
            var be;
        	console.log('this.maxTime = ' + this.maxTime);
            while (bd < this.maxTime) {
            	console.log('a7.widgetContentClass = ' + a7.widgetContentClass);
                a8 = this.start.clone().time(bd);
                a9 = a8.minutes();
                be = '<td class="fc-axis fc-time ' + a7.widgetContentClass + '" ' + a7.axisStyleAttr() + ">" + ((!ba || !a9) ? "<span>" + a2(a8.format(this.axisFormat)) + "</span>" : "") + "</td>";
                bb += "<tr " + (!a9 ? "" : 'class="fc-minor"') + ">" + (!bc ? be : "") + '<td class="' + a7.widgetContentClass + '"/>' + (bc ? be : "") + "</tr>";
                bd.add(this.slotDuration)
            }
            return bb
        },
        processOptions: function() {
            var a7 = this.view;
            var a9 = a7.opt("slotDuration");
            var a8 = a7.opt("snapDuration");
            a9 = z.duration(a9);
            a8 = a8 ? z.duration(a8) : a9;
            this.slotDuration = a9;
            this.snapDuration = a8;
            this.minTime = z.duration(a7.opt("minTime"));
            this.maxTime = z.duration(a7.opt("maxTime"));
            this.axisFormat = a7.opt("axisFormat") || a7.opt("smallTimeFormat")
        },
        computeColHeadFormat: function() {
            if (this.colCnt > 1) {
                return this.view.opt("dayOfMonthFormat")
            } else {
                return "dddd"
            }
        },
        computeEventTimeFormat: function() {
            return this.view.opt("noMeridiemTimeFormat")
        },
        computeDisplayEventEnd: function() {
            return true
        },
        updateCells: function() {
            var a7 = this.view;
            var a9 = [];
            var a8;
            a8 = this.start.clone();
            while (a8.isBefore(this.end)) {
                a9.push({
                    day: a8.clone()
                });
                a8.add(1, "day");
                a8 = a7.skipHiddenDays(a8)
            }
            if (this.isRTL) {
                a9.reverse()
            }
            this.colData = a9;
            this.colCnt = a9.length;
            this.rowCnt = Math.ceil((this.maxTime - this.minTime) / this.snapDuration)
        },
        computeCellRange: function(a7) {
            var a9 = this.computeSnapTime(a7.row);
            var ba = this.view.calendar.rezoneDate(a7.day).time(a9);
            var a8 = ba.clone().add(this.snapDuration);
            return {
                start: ba,
                end: a8
            }
        },
        getColEl: function(a7) {
            return this.dayEls.eq(a7)
        },
        computeSnapTime: function(a7) {
            return z.duration(this.minTime + this.snapDuration * a7)
        },
        rangeToSegs: function(ba) {
            var bc = this.colCnt;
            var a8 = [];
            var a7;
            var bb;
            var bd;
            var a9;
            ba = {
                start: ba.start.clone().stripZone(),
                end: ba.end.clone().stripZone()
            };
            for (bb = 0; bb < bc; bb++) {
                bd = this.colData[bb].day;
                a9 = {
                    start: bd.clone().time(this.minTime),
                    end: bd.clone().time(this.maxTime)
                };
                a7 = aI(ba, a9);
                if (a7) {
                    a7.col = bb;
                    a8.push(a7)
                }
            }
            return a8
        },
        resize: function() {
            this.computeSlatTops();
            this.updateSegVerticals()
        },
        computeRowCoords: function() {
            var ba = this.el.offset().top;
            var a7 = [];
            var a8;
            var a9;
            for (a8 = 0; a8 < this.rowCnt; a8++) {
                a9 = {
                    top: ba + this.computeTimeTop(this.computeSnapTime(a8))
                };
                if (a8 > 0) {
                    a7[a8 - 1].bottom = a9.top
                }
                a7.push(a9)
            }
            a9.bottom = a9.top + this.computeTimeTop(this.computeSnapTime(a8));
            return a7
        },
        computeDateTop: function(a7, a8) {
            return this.computeTimeTop(z.duration(a7.clone().stripZone() - a8.clone().stripTime()))
        },
        computeTimeTop: function(bc) {
            var ba = (bc - this.minTime) / this.slotDuration;
            var a9;
            var a8;
            var bb;
            var a7;
            ba = Math.max(0, ba);
            ba = Math.min(this.slatEls.length, ba);
            a9 = Math.floor(ba);
            a8 = ba - a9;
            bb = this.slatTops[a9];
            if (a8) {
                a7 = this.slatTops[a9 + 1];
                return bb + (a7 - bb) * a8
            } else {
                return bb
            }
        },
        computeSlatTops: function() {
            var a8 = [];
            var a7;
            this.slatEls.each(function(a9, ba) {
                a7 = aK(ba).position().top;
                a8.push(a7)
            });
            a8.push(a7 + this.slatEls.last().outerHeight());
            this.slatTops = a8
        },
        renderDrag: function(a9, a7) {
            var a8;
            if (a7) {
                this.renderRangeHelper(a9, a7);
                a8 = this.view.opt("dragOpacity");
                if (a8 !== undefined) {
                    this.helperEl.css("opacity", a8)
                }
                return true
            } else {
                this.renderHighlight(this.view.calendar.ensureVisibleEventRange(a9))
            }
        },
        destroyDrag: function() {
            this.destroyHelper();
            this.destroyHighlight()
        },
        renderEventResize: function(a8, a7) {
            this.renderRangeHelper(a8, a7)
        },
        destroyEventResize: function() {
            this.destroyHelper()
        },
        renderHelper: function(bd, bb) {
            var a9 = this.eventsToSegs([bd]);
            var a8;
            var bc, a7;
            var ba;
            a9 = this.renderFgSegEls(a9);
            a8 = this.renderSegTable(a9);
            for (bc = 0; bc < a9.length; bc++) {
                a7 = a9[bc];
                if (bb && bb.col === a7.col) {
                    ba = bb.el;
                    a7.el.css({
                        left: ba.css("left"),
                        right: ba.css("right"),
                        "margin-left": ba.css("margin-left"),
                        "margin-right": ba.css("margin-right")
                    })
                }
            }
            this.helperEl = aK('<div class="fc-helper-skeleton"/>').append(a8).appendTo(this.el)
        },
        destroyHelper: function() {
            if (this.helperEl) {
                this.helperEl.remove();
                this.helperEl = null
            }
        },
        renderSelection: function(a7) {
            if (this.view.opt("selectHelper")) {
                this.renderRangeHelper(a7)
            } else {
                this.renderHighlight(a7)
            }
        },
        destroySelection: function() {
            this.destroyHelper();
            this.destroyHighlight()
        },
        renderFill: function(bg, a8, bf) {
            var bc;
            var a9;
            var bi;
            var ba, bj;
            var bh;
            var be;
            var a7;
            var bd, bb;
            if (a8.length) {
                a8 = this.renderFillSegEls(bg, a8);
                bc = this.groupSegCols(a8);
                bf = bf || bg.toLowerCase();
                a9 = aK('<div class="fc-' + bf + '-skeleton"><table><tr/></table></div>');
                bi = a9.find("tr");
                for (ba = 0; ba < bc.length; ba++) {
                    bj = bc[ba];
                    bh = aK("<td/>").appendTo(bi);
                    if (bj.length) {
                        be = aK('<div class="fc-' + bf + '-container"/>').appendTo(bh);
                        a7 = this.colData[ba].day;
                        for (bd = 0; bd < bj.length; bd++) {
                            bb = bj[bd];
                            be.append(bb.el.css({
                                top: this.computeDateTop(bb.start, a7),
                                bottom: -this.computeDateTop(bb.end, a7)
                            }))
                        }
                    }
                }
                this.bookendCells(bi, bg);
                this.el.append(a9);
                this.elsByFill[bg] = a9
            }
            return a8
        }
    });
    h.mixin({
        eventSkeletonEl: null,
        renderFgSegs: function(a7) {
            a7 = this.renderFgSegEls(a7);
            this.el.append(this.eventSkeletonEl = aK('<div class="fc-content-skeleton"/>').append(this.renderSegTable(a7)));
            return a7
        },
        destroyFgSegs: function(a7) {
            if (this.eventSkeletonEl) {
                this.eventSkeletonEl.remove();
                this.eventSkeletonEl = null
            }
        },
        renderSegTable: function(a7) {
            var a9 = aK("<table><tr/></table>");
            var be = a9.find("tr");
            var ba;
            var bc, bb;
            var a8, bf;
            var bd;
            ba = this.groupSegCols(a7);
            this.computeSegVerticals(a7);
            for (a8 = 0; a8 < ba.length; a8++) {
                bf = ba[a8];
                aB(bf);
                bd = aK('<div class="fc-event-container"/>');
                for (bc = 0; bc < bf.length; bc++) {
                    bb = bf[bc];
                    bb.el.css(this.generateSegPositionCss(bb));
                    if (bb.bottom - bb.top < 30) {
                        bb.el.addClass("fc-short")
                    }
                    bd.append(bb.el)
                }
                be.append(aK("<td/>").append(bd))
            }
            this.bookendCells(be, "eventSkeleton");
            return a9
        },
        updateSegVerticals: function() {
            var a7 = (this.segs || []).concat(this.businessHourSegs || []);
            var a8;
            this.computeSegVerticals(a7);
            for (a8 = 0; a8 < a7.length; a8++) {
                a7[a8].el.css(this.generateSegVerticalCss(a7[a8]))
            }
        },
        computeSegVerticals: function(a8) {
            var a9, a7;
            for (a9 = 0; a9 < a8.length; a9++) {
                a7 = a8[a9];
                a7.top = this.computeDateTop(a7.start, a7.start);
                a7.bottom = this.computeDateTop(a7.end, a7.start)
            }
        },
        fgSegHtml: function(be, a9) {
            var bg = this.view;
            var a8 = be.event;
            var a7 = bg.isEventDraggable(a8);
            var bb = !a9 && be.isEnd && bg.isEventResizable(a8);
            var bd = this.getSegClasses(be, a7, bb);
            var bh = this.getEventSkinCss(a8);
            var bc;
            var bf;
            var ba;
            bd.unshift("fc-time-grid-event");
            if (bg.isMultiDayEvent(a8)) {
                if (be.isStart || be.isEnd) {
                    bc = this.getEventTimeText(be);
                    bf = this.getEventTimeText(be, "LT");
                    ba = this.getEventTimeText({
                        start: be.start
                    })
                }
            } else {
                bc = this.getEventTimeText(a8);
                bf = this.getEventTimeText(a8, "LT");
                ba = this.getEventTimeText({
                    start: a8.start
                })
            }
            return '<a class="' + bd.join(" ") + '"' + (a8.url ? ' href="' + a2(a8.url) + '"' : "") + (bh ? ' style="' + bh + '"' : "") + '><div class="fc-content">' + (bc ? '<div class="fc-time" data-start="' + a2(ba) + '" data-full="' + a2(bf) + '"><span>' + a2(bc) + "</span></div>" : "") + (a8.title ? '<div class="fc-title">' + a2(a8.title) + "</div>" : "") + '</div><div class="fc-bg"/>' + (bb ? '<div class="fc-resizer"/>' : "") + "</a>"
        },
        generateSegPositionCss: function(a7) {
            var ba = this.view.opt("slotEventOverlap");
            var a8 = a7.backwardCoord;
            var a9 = a7.forwardCoord;
            var bc = this.generateSegVerticalCss(a7);
            var bd;
            var bb;
            if (ba) {
                a9 = Math.min(1, a8 + (a9 - a8) * 2)
            }
            if (this.isRTL) {
                bd = 1 - a9;
                bb = a8
            } else {
                bd = a8;
                bb = 1 - a9
            }
            bc.zIndex = a7.level + 1;
            bc.left = bd * 100 + "%";
            bc.right = bb * 100 + "%";
            if (ba && a7.forwardPressure) {
                bc[this.isRTL ? "marginLeft" : "marginRight"] = 10 * 2
            }
            return bc
        },
        generateSegVerticalCss: function(a7) {
            return {
                top: a7.top,
                bottom: -a7.bottom
            }
        },
        groupSegCols: function(a8) {
            var a7 = [];
            var a9;
            for (a9 = 0; a9 < this.colCnt; a9++) {
                a7.push([])
            }
            for (a9 = 0; a9 < a8.length; a9++) {
                a7[a8[a9].col].push(a8[a9])
            }
            return a7
        }
    });

    function aB(a7) {
        var a9;
        var ba;
        var a8;
        a7.sort(y);
        a9 = F(a7);
        am(a9);
        if ((ba = a9[0])) {
            for (a8 = 0; a8 < ba.length; a8++) {
                au(ba[a8])
            }
            for (a8 = 0; a8 < ba.length; a8++) {
                Q(ba[a8], 0, 0)
            }
        }
    }

    function F(a8) {
        var bb = [];
        var ba, a7;
        var a9;
        for (ba = 0; ba < a8.length; ba++) {
            a7 = a8[ba];
            for (a9 = 0; a9 < bb.length; a9++) {
                if (!a5(a7, bb[a9]).length) {
                    break
                }
            }
            a7.level = a9;
            (bb[a9] || (bb[a9] = [])).push(a7)
        }
        return bb
    }

    function am(bb) {
        var ba, bc;
        var a9, a7;
        var a8;
        for (ba = 0; ba < bb.length; ba++) {
            bc = bb[ba];
            for (a9 = 0; a9 < bc.length; a9++) {
                a7 = bc[a9];
                a7.forwardSegs = [];
                for (a8 = ba + 1; a8 < bb.length; a8++) {
                    a5(a7, bb[a8], a7.forwardSegs)
                }
            }
        }
    }

    function au(a8) {
        var a7 = a8.forwardSegs;
        var bb = 0;
        var ba, a9;
        if (a8.forwardPressure === undefined) {
            for (ba = 0; ba < a7.length; ba++) {
                a9 = a7[ba];
                au(a9);
                bb = Math.max(bb, 1 + a9.forwardPressure)
            }
            a8.forwardPressure = bb
        }
    }

    function Q(a8, ba, bb) {
        var a7 = a8.forwardSegs;
        var a9;
        if (a8.forwardCoord === undefined) {
            if (!a7.length) {
                a8.forwardCoord = 1
            } else {
                a7.sort(aO);
                Q(a7[0], ba + 1, bb);
                a8.forwardCoord = a7[0].backwardCoord
            }
            a8.backwardCoord = a8.forwardCoord - (a8.forwardCoord - bb) / (ba + 1);
            for (a9 = 0; a9 < a7.length; a9++) {
                Q(a7[a9], 0, a8.forwardCoord)
            }
        }
    }

    function a5(a7, ba, a9) {
        a9 = a9 || [];
        for (var a8 = 0; a8 < ba.length; a8++) {
            if (g(a7, ba[a8])) {
                a9.push(ba[a8])
            }
        }
        return a9
    }

    function g(a8, a7) {
        return a8.bottom > a7.top && a8.top < a7.bottom
    }

    function aO(a8, a7) {
        return a7.forwardPressure - a8.forwardPressure || (a8.backwardCoord || 0) - (a7.backwardCoord || 0) || y(a8, a7)
    }
    var a1 = aY.View = aR.extend({
        type: null,
        name: null,
        calendar: null,
        options: null,
        coordMap: null,
        el: null,
        start: null,
        end: null,
        intervalStart: null,
        intervalEnd: null,
        intervalDuration: null,
        intervalUnit: null,
        isSelected: false,
        scrollerEl: null,
        scrollTop: null,
        widgetHeaderClass: null,
        widgetContentClass: null,
        highlightStateClass: null,
        nextDayThreshold: null,
        isHiddenDayHash: null,
        documentMousedownProxy: null,
        constructor: function(a9, a8, a7) {
            this.calendar = a9;
            this.options = a8;
            this.type = this.name = a7;
            this.nextDayThreshold = z.duration(this.opt("nextDayThreshold"));
            this.initTheming();
            this.initHiddenDays();
            this.documentMousedownProxy = aK.proxy(this, "documentMousedown");
            this.initialize()
        },
        initialize: function() {},
        opt: function(a7) {
            var a8;
            a8 = this.options[a7];
            if (a8 !== undefined) {
                return a8
            }
            a8 = this.calendar.options[a7];
            if (aK.isPlainObject(a8) && !ac(a7)) {
                return H(a8, this.type)
            }
            return a8
        },
        trigger: function(a7, a9) {
            var a8 = this.calendar;
            return a8.trigger.apply(a8, [a7, a9 || this].concat(Array.prototype.slice.call(arguments, 2), [this]))
        },
        setDate: function(a7) {
            this.setRange(this.computeRange(a7))
        },
        setRange: function(a7) {
            aK.extend(this, a7)
        },
        computeRange: function(a9) {
            var ba = z.duration(this.opt("duration") || this.constructor.duration || {
                days: 1
            });
            var bd = M(ba);
            var bb = a9.clone().startOf(bd);
            var a8 = bb.clone().add(ba);
            var bc, a7;
            if (aE("days", ba)) {
                bb.stripTime();
                a8.stripTime()
            } else {
                if (!bb.hasTime()) {
                    bb = this.calendar.rezoneDate(bb)
                }
                if (!a8.hasTime()) {
                    a8 = this.calendar.rezoneDate(a8)
                }
            }
            bc = bb.clone();
            bc = this.skipHiddenDays(bc);
            a7 = a8.clone();
            a7 = this.skipHiddenDays(a7, -1, true);
            return {
                intervalDuration: ba,
                intervalUnit: bd,
                intervalStart: bb,
                intervalEnd: a8,
                start: bc,
                end: a7
            }
        },
        computePrevDate: function(a7) {
            return this.skipHiddenDays(a7.clone().startOf(this.intervalUnit).subtract(this.intervalDuration), -1)
        },
        computeNextDate: function(a7) {
            return this.skipHiddenDays(a7.clone().startOf(this.intervalUnit).add(this.intervalDuration))
        },
        computeTitle: function() {
            return this.formatRange({
                start: this.intervalStart,
                end: this.intervalEnd
            }, this.opt("titleFormat") || this.computeTitleFormat(), this.opt("titleRangeSeparator"))
        },
        computeTitleFormat: function() {
            if (this.intervalUnit == "year") {
                return "YYYY"
            } else {
                if (this.intervalUnit == "month") {
                    return this.opt("monthYearFormat")
                } else {
                    if (this.intervalDuration.as("days") > 1) {
                        return "ll"
                    } else {
                        return "LL"
                    }
                }
            }
        },
        formatRange: function(a9, a8, ba) {
            var a7 = a9.end;
            if (!a7.hasTime()) {
                a7 = a7.clone().subtract(1)
            }
            return O(a9.start, a7, a8, ba, this.opt("isRTL"))
        },
        renderView: function() {
            this.render();
            this.updateSize();
            this.initializeScroll();
            this.trigger("viewRender", this, this, this.el);
            aK(document).on("mousedown", this.documentMousedownProxy)
        },
        render: function() {},
        destroyView: function() {
            this.unselect();
            this.destroyViewEvents();
            this.destroy();
            this.trigger("viewDestroy", this, this, this.el);
            aK(document).off("mousedown", this.documentMousedownProxy)
        },
        destroy: function() {
            this.el.empty()
        },
        initTheming: function() {
            var a7 = this.opt("theme") ? "ui" : "fc";
            this.widgetHeaderClass = a7 + "-widget-header";
            this.widgetContentClass = a7 + "-widget-content";
            this.highlightStateClass = a7 + "-state-highlight"
        },
        updateSize: function(a7) {
            if (a7) {
                this.recordScroll()
            }
            this.updateHeight();
            this.updateWidth()
        },
        updateWidth: function() {},
        updateHeight: function() {
            var a7 = this.calendar;
            this.setHeight(a7.getSuggestedViewHeight(), a7.isHeightAuto())
        },
        setHeight: function(a8, a7) {},
        computeScrollerHeight: function(a8, a7) {
            var ba;
            var a9;
            a7 = a7 || this.scrollerEl;
            ba = this.el.add(a7);
            ba.css({
                position: "relative",
                left: -1
            });
            a9 = this.el.outerHeight() - a7.height();
            ba.css({
                position: "",
                left: ""
            });
            return a8 - a9
        },
        initializeScroll: function() {},
        recordScroll: function() {
            if (this.scrollerEl) {
                this.scrollTop = this.scrollerEl.scrollTop()
            }
        },
        restoreScroll: function() {
            if (this.scrollTop !== null) {
                this.scrollerEl.scrollTop(this.scrollTop)
            }
        },
        renderViewEvents: function(a7) {
            this.renderEvents(a7);
            this.eventSegEach(function(a8) {
                this.trigger("eventAfterRender", a8.event, a8.event, a8.el)
            });
            this.trigger("eventAfterAllRender")
        },
        renderEvents: function() {},
        destroyViewEvents: function() {
            this.eventSegEach(function(a7) {
                this.trigger("eventDestroy", a7.event, a7.event, a7.el)
            });
            this.destroyEvents()
        },
        destroyEvents: function() {},
        resolveEventEl: function(a8, a7) {
            var a9 = this.trigger("eventRender", a8, a8, a7);
            if (a9 === false) {
                a7 = null
            } else {
                if (a9 && a9 !== true) {
                    a7 = aK(a9)
                }
            }
            return a7
        },
        showEvent: function(a7) {
            this.eventSegEach(function(a8) {
                a8.el.css("visibility", "")
            }, a7)
        },
        hideEvent: function(a7) {
            this.eventSegEach(function(a8) {
                a8.el.css("visibility", "hidden")
            }, a7)
        },
        eventSegEach: function(ba, a9) {
            var a7 = this.getEventSegs();
            var a8;
            for (a8 = 0; a8 < a7.length; a8++) {
                if (!a9 || a7[a8].event._id === a9._id) {
                    ba.call(this, a7[a8])
                }
            }
        },
        getEventSegs: function() {
            return []
        },
        isEventDraggable: function(a7) {
            var a8 = a7.source || {};
            return a4(a7.startEditable, a8.startEditable, this.opt("eventStartEditable"), a7.editable, a8.editable, this.opt("editable"))
        },
        reportEventDrop: function(bb, ba, a8, a9) {
            var bd = this.calendar;
            var bc = bd.mutateEvent(bb, ba);
            var a7 = function() {
                bc.undo();
                bd.reportEventChange()
            };
            this.triggerEventDrop(bb, bc.dateDelta, a7, a8, a9);
            bd.reportEventChange()
        },
        triggerEventDrop: function(ba, bb, a7, a8, a9) {
            this.trigger("eventDrop", a8[0], ba, bb, a7, a9, {})
        },
        reportExternalDrop: function(be, bb, a8, ba, bd) {
            var bc = be.eventProps;
            var a7;
            var a9;
            if (bc) {
                a7 = aK.extend({}, bc, bb);
                a9 = this.calendar.renderEvent(a7, be.stick)[0]
            }
            this.triggerExternalDrop(a9, bb, a8, ba, bd)
        },
        triggerExternalDrop: function(ba, a9, a7, a8, bb) {
            this.trigger("drop", a7[0], a9.start, a8, bb);
            if (ba) {
                this.trigger("eventReceive", null, ba)
            }
        },
        renderDrag: function(a8, a7) {},
        destroyDrag: function() {},
        isEventResizable: function(a7) {
            var a8 = a7.source || {};
            return a4(a7.durationEditable, a8.durationEditable, this.opt("eventDurationEditable"), a7.editable, a8.editable, this.opt("editable"))
        },
        reportEventResize: function(bb, ba, a8, a9) {
            var bd = this.calendar;
            var bc = bd.mutateEvent(bb, {
                end: ba
            });
            var a7 = function() {
                bc.undo();
                bd.reportEventChange()
            };
            this.triggerEventResize(bb, bc.durationDelta, a7, a8, a9);
            bd.reportEventChange()
        },
        triggerEventResize: function(bb, a8, a7, a9, ba) {
            this.trigger("eventResize", a9[0], bb, a8, a7, ba, {})
        },
        select: function(a7, a8) {
            this.unselect(a8);
            this.renderSelection(a7);
            this.reportSelection(a7, a8)
        },
        renderSelection: function(a7) {},
        reportSelection: function(a7, a8) {
            this.isSelected = true;
            this.trigger("select", null, a7.start, a7.end, a8)
        },
        unselect: function(a7) {
            if (this.isSelected) {
                this.isSelected = false;
                this.destroySelection();
                this.trigger("unselect", null, a7)
            }
        },
        destroySelection: function() {},
        documentMousedown: function(a7) {
            var a8;
            if (this.isSelected && this.opt("unselectAuto") && V(a7)) {
                a8 = this.opt("unselectCancel");
                if (!a8 || !aK(a7.target).closest(a8).length) {
                    this.unselect(a7)
                }
            }
        },
        initHiddenDays: function() {
            var a8 = this.opt("hiddenDays") || [];
            var ba = [];
            var a7 = 0;
            var a9;
            if (this.opt("weekends") === false) {
                a8.push(0, 6)
            }
            for (a9 = 0; a9 < 7; a9++) {
                if (!(ba[a9] = aK.inArray(a9, a8) !== -1)) {
                    a7++
                }
            }
            if (!a7) {
                throw "invalid hiddenDays"
            }
			if (this.opt("weekends") === true) {
				ba[0] = true;
			}            
            this.isHiddenDayHash = ba
        },
        isHiddenDay: function(a7) {
            if (z.isMoment(a7)) {
                a7 = a7.day()
            }
            return this.isHiddenDayHash[a7]
        },
        skipHiddenDays: function(a9, ba, a7) {
            var a8 = a9.clone();
            ba = ba || 1;
            while (this.isHiddenDayHash[(a8.day() + (a7 ? ba : 0) + 7) % 7]) {
                a8.add(ba, "days")
            }
            return a8
        },
        computeDayRange: function(a8) {
            var bb = a8.start.clone().stripTime();
            var a7 = a8.end;
            var ba = null;
            var a9;
            if (a7) {
                ba = a7.clone().stripTime();
                a9 = +a7.time();
                if (a9 && a9 >= this.nextDayThreshold) {
                    ba.add(1, "days")
                }
            }
            if (!a7 || ba <= bb) {
                ba = bb.clone().add(1, "days")
            }
            return {
                start: bb,
                end: ba
            }
        },
        isMultiDayEvent: function(a8) {
            var a7 = this.computeDayRange(a8);
            return a7.end.diff(a7.start, "days") > 1
        }
    });

    function p(br, bn) {
        var bW = this;
        bn = bn || {};
        var bB = aV({}, T, bn);
        var bN;
        if (bB.lang in aM) {
            bN = aM[bB.lang]
        } else {
            bN = aM[T.lang]
        }
        if (bN) {
            bB = aV({}, T, bN, bn)
        }
        if (bB.isRTL) {
            bB = aV({}, T, ab, bN || {}, bn)
        }
        bW.options = bB;
        bW.render = bK;
        bW.destroy = bY;
        bW.refetchEvents = bk;
        bW.reportEvents = bp;
        bW.reportEventChange = b1;
        bW.rerenderEvents = bf;
        bW.changeView = bi;
        bW.select = bX;
        bW.unselect = bj;
        bW.prev = bG;
        bW.next = bb;
        bW.prevYear = b0;
        bW.nextYear = a8;
        bW.today = bs;
        bW.gotoDate = bJ;
        bW.incrementDate = bw;
        bW.zoomTo = b5;
        bW.getDate = bx;
        bW.getCalendar = bQ;
        bW.getView = bE;
        bW.option = bT;
        bW.trigger = bI;
        bW.isValidViewType = bO;
        bW.getViewButtonText = bz;
        var bM = R(Z(bB.lang));
        if (bB.monthNames) {
            bM._months = bB.monthNames
        }
        if (bB.monthNamesShort) {
            bM._monthsShort = bB.monthNamesShort
        }
        if (bB.dayNames) {
            bM._weekdays = bB.dayNames
        }
        if (bB.dayNamesShort) {
            bM._weekdaysShort = bB.dayNamesShort
        }
        if (bB.firstDay != null) {
            var ba = R(bM._week);
            ba.dow = bB.firstDay;
            bM._week = ba
        }
        bW.defaultAllDayEventDuration = z.duration(bB.defaultAllDayEventDuration);
        bW.defaultTimedEventDuration = z.duration(bB.defaultTimedEventDuration);
        bW.moment = function() {
            var b7;
            if (bB.timezone === "local") {
                b7 = aY.moment.apply(null, arguments);
                if (b7.hasTime()) {
                    b7.local()
                }
            } else {
                if (bB.timezone === "UTC") {
                    b7 = aY.moment.utc.apply(null, arguments)
                } else {
                    b7 = aY.moment.parseZone.apply(null, arguments)
                }
            }
            if ("_locale" in b7) {
                b7._locale = bM
            } else {
                b7._lang = bM
            }
            return b7
        };
        bW.getIsAmbigTimezone = function() {
            return bB.timezone !== "local" && bB.timezone !== "UTC"
        };
        bW.rezoneDate = function(b7) {
            return bW.moment(b7.toArray())
        };
        bW.getNow = function() {
            var b7 = bB.now;
            if (typeof b7 === "function") {
                b7 = b7()
            }
            return bW.moment(b7)
        };
        bW.calculateWeekNumber = function(b8) {
            var b7 = bB.weekNumberCalculation;
            if (typeof b7 === "function") {
                return b7(b8)
            } else {
                if (b7 === "local") {
                    return b8.week()
                } else {
                    if (b7.toUpperCase() === "ISO") {
                        return b8.isoWeek()
                    }
                }
            }
        };
        bW.getEventEnd = function(b7) {
            if (b7.end) {
                return b7.end.clone()
            } else {
                return bW.getDefaultEventEnd(b7.allDay, b7.start)
            }
        };
        bW.getDefaultEventEnd = function(b8, b9) {
            var b7 = b9.clone();
            if (b8) {
                b7.stripTime().add(bW.defaultAllDayEventDuration)
            } else {
                b7.add(bW.defaultTimedEventDuration)
            }
            if (bW.getIsAmbigTimezone()) {
                b7.stripZone()
            }
            return b7
        };

        function bA(b7) {
            return (b7.locale || b7.lang).call(b7, bB.lang).humanize()
        }
        o.call(bW, bB);
        var a7 = bW.isFetchNeeded;
        var b4 = bW.fetchEvents;
        var bU = br[0];
        var bd;
        var bL;
        var bm;
        var b6;
        var bH = {};
        var bl;
        var bg;
        var b3;
        var bV = 0;
        var b2;
        var bD = [];
        if (bB.defaultDate != null) {
            b2 = bW.moment(bB.defaultDate)
        } else {
            b2 = bW.getNow()
        }

        function bK(b7) {
            if (!bm) {
                bC()
            } else {
                if (bS()) {
                    bP();
                    by(b7)
                }
            }
        }

        function bC() {
            b6 = bB.theme ? "ui" : "fc";
            br.addClass("fc");
            if (bB.isRTL) {
                br.addClass("fc-rtl")
            } else {
                br.addClass("fc-ltr")
            }
            if (bB.theme) {
                br.addClass("ui-widget")
            } else {
                br.addClass("fc-unthemed")
            }
            bm = aK("<div class='fc-view-container'/>").prependTo(br);
            bd = new ae(bW, bB);
            bL = bd.render();
            if (bL) {
                br.prepend(bL)
            }
            bi(bB.defaultView);
            if (bB.handleWindowResize) {
                b3 = a6(bo, bB.windowResizeDelay);
                aK(window).resize(b3)
            }
        }

        function bY() {
            if (bl) {
                bl.destroyView()
            }
            bd.destroy();
            bm.remove();
            br.removeClass("fc fc-ltr fc-rtl fc-unthemed ui-widget");
            aK(window).unbind("resize", b3)
        }

        function bS() {
            return br.is(":visible")
        }

        function bi(b7) {
            by(0, b7)
        }

        function by(b8, b7) {
            bV++;
            if (bl && b7 && bl.type !== b7) {
                bd.deactivateButton(bl.type);
                bc();
                if (bl.start) {
                    bl.destroyView()
                }
                bl.el.remove();
                bl = null
            }
            if (!bl && b7) {
                bl = bZ(b7);
                bl.el = aK("<div class='fc-view fc-" + b7 + "-view' />").appendTo(bm);
                bd.activateButton(b7)
            }
            if (bl) {
                if (b8 < 0) {
                    b2 = bl.computePrevDate(b2)
                } else {
                    if (b8 > 0) {
                        b2 = bl.computeNextDate(b2)
                    }
                }
                if (!bl.start || b8 || !b2.isWithin(bl.intervalStart, bl.intervalEnd)) {
                    if (bS()) {
                        bc();
                        if (bl.start) {
                            bl.destroyView()
                        }
                        bl.setDate(b2);
                        bl.renderView();
                        bq();
                        bt();
                        bF();
                        bh()
                    }
                }
            }
            bq();
            bV--
        }

        function bZ(b7) {
            var b8 = a9(b7);
            return new b8["class"](bW, b8.options, b7)
        }

        function a9(cb) {
            var cf = bB.defaultButtonText || {};
            var ch = bB.buttonText || {};
            var cc = bB.views || {};
            var cd = cb;
            var ce = [];
            var b7;
            var cg;
            var b8, cj, ca = false;
            var ci;
            if (bH[cb]) {
                return bH[cb]
            }

            function b9(ck) {
                if (typeof ck === "function") {
                    cg = ck
                } else {
                    if (typeof ck === "object") {
                        aK.extend(b7, ck)
                    }
                }
            }
            while (cd && !cg) {
                b7 = {};
                b9(ax[cd]);
                b9(cc[cd]);
                ce.unshift(b7);
                cd = b7.type
            }
            ce.unshift({});
            b7 = aK.extend.apply(aK, ce);
            if (cg) {
                b8 = b7.duration || cg.duration;
                if (b8) {
                    b8 = z.duration(b8);
                    cj = M(b8);
                    ca = aE(cj, b8) === 1
                }
                if (ca && cc[cj]) {
                    b7 = aK.extend({}, cc[cj], b7)
                }
                ci = ch[cb] || (ca ? ch[cj] : null) || cf[cb] || (ca ? cf[cj] : null) || b7.buttonText || cg.buttonText || (b8 ? bA(b8) : null) || cb;
                return (bH[cb] = {
                    "class": cg,
                    options: b7,
                    buttonText: ci
                })
            }
        }

        function bO(b7) {
            return Boolean(a9(b7))
        }

        function bz(b7) {
            var b8 = a9(b7);
            if (b8) {
                return b8.buttonText
            }
        }
        bW.getSuggestedViewHeight = function() {
            if (bg === undefined) {
                bP()
            }
            return bg
        };
        bW.isHeightAuto = function() {
            return bB.contentHeight === "auto" || bB.height === "auto"
        };

        function bu(b7) {
            if (bS()) {
                if (b7) {
                    be()
                }
                bV++;
                bl.updateSize(true);
                bV--;
                return true
            }
        }

        function bP() {
            if (bS()) {
                be()
            }
        }

        function be() {
            if (typeof bB.contentHeight === "number") {
                bg = bB.contentHeight
            } else {
                if (typeof bB.height === "number") {
                    bg = bB.height - (bL ? bL.outerHeight(true) : 0)
                } else {
                    bg = Math.round(bm.width() / Math.max(bB.aspectRatio, 0.5))
                }
            }
        }

        function bo(b7) {
            if (!bV && b7.target === window && bl.start) {
                if (bu(true)) {
                    bl.trigger("windowResize", bU)
                }
            }
        }

        function bk() {
            bv();
            bR()
        }

        function bf() {
            if (bS()) {
                bc();
                bl.destroyViewEvents();
                bl.renderViewEvents(bD);
                bq()
            }
        }

        function bv() {
            bc();
            bl.destroyViewEvents();
            bq()
        }

        function bh() {
            if (!bB.lazyFetching || a7(bl.start, bl.end)) {
                bR()
            } else {
                bf()
            }
        }

        function bR() {
            b4(bl.start, bl.end)
        }

        function bp(b7) {
            bD = b7;
            bf()
        }

        function b1() {
            bf()
        }

        function bt() {
            bd.updateTitle(bl.computeTitle())
        }

        function bF() {
            var b7 = bW.getNow();
            if (b7.isWithin(bl.intervalStart, bl.intervalEnd)) {
                bd.disableButton("today")
            } else {
                bd.enableButton("today")
            }
        }

        function bX(b8, b7) {
            b8 = bW.moment(b8);
            if (b7) {
                b7 = bW.moment(b7)
            } else {
                if (b8.hasTime()) {
                    b7 = b8.clone().add(bW.defaultTimedEventDuration)
                } else {
                    b7 = b8.clone().add(bW.defaultAllDayEventDuration)
                }
            }
            bl.select({
                start: b8,
                end: b7
            })
        }

        function bj() {
            if (bl) {
                bl.unselect()
            }
        }

        function bG() {
            by(-1)
        }

        function bb() {
            by(1)
        }

        function b0() {
            b2.add(-1, "years");
            by()
        }

        function a8() {
            b2.add(1, "years");
            by()
        }

        function bs() {
            b2 = bW.getNow();
            by()
        }

        function bJ(b7) {
            b2 = bW.moment(b7);
            by()
        }

        function bw(b7) {
            b2.add(z.duration(b7));
            by()
        }

        function b5(b9, b7) {
            var b8;
            var ca;
            if (!b7 || !bO(b7)) {
                b7 = b7 || "day";
                b8 = bd.getViewsWithButtons().join(" ");
                ca = b8.match(new RegExp("\\w+" + aQ(b7)));
                if (!ca) {
                    ca = b8.match(/\w+Day/)
                }
                b7 = ca ? ca[0] : "agendaDay"
            }
            b2 = b9;
            bi(b7)
        }

        function bx() {
            return b2.clone()
        }

        function bc() {
            bm.css({
                width: "100%",
                height: bm.height(),
                overflow: "hidden"
            })
        }

        function bq() {
            bm.css({
                width: "",
                height: "",
                overflow: ""
            })
        }

        function bQ() {
            return bW
        }

        function bE() {
            return bl
        }

        function bT(b7, b8) {
            if (b8 === undefined) {
                return bB[b7]
            }
            if (b7 == "height" || b7 == "contentHeight" || b7 == "aspectRatio") {
                bB[b7] = b8;
                bu(true)
            }
        }

        function bI(b7, b8) {
            if (bB[b7]) {
                return bB[b7].apply(b8 || bU, Array.prototype.slice.call(arguments, 2))
            }
        }
    }

    function ae(bc, bl) {
        var bk = this;
        bk.render = a8;
        bk.destroy = bf;
        bk.updateTitle = bd;
        bk.activateButton = a7;
        bk.deactivateButton = bi;
        bk.disableButton = bb;
        bk.enableButton = be;
        bk.getViewsWithButtons = ba;
        var a9 = aK();
        var bg = [];
        var bh;

        function a8() {
            var bm = bl.header;
            bh = bl.theme ? "ui" : "fc";
            if (bm) {
                a9 = aK("<div class='fc-toolbar'/>").append(bj("left")).append(bj("right")).append(bj("center")).append('<div class="fc-clear"/>');
                return a9
            }
        }

        function bf() {
            a9.remove()
        }

        function bj(bm) {
            var bn = aK('<div class="fc-' + bm + '"/>');
            var bo = bl.header[bm];
            if (bo) {
                aK.each(bo.split(" "), function(bp) {
                    var bq = aK();
                    var br = true;
                    var bs;
                    aK.each(this.split(","), function(bx, bD) {
                        var bA;
                        var bu;
                        var bw;
                        var bC;
                        var bB;
                        var bz;
                        var bt;
                        var bv;
                        var by;
                        if (bD == "title") {
                            bq = bq.add(aK("<h2>&nbsp;</h2>"));
                            br = false
                        } else {
                            if (bc[bD]) {
                                bA = function() {
                                    bc[bD]()
                                }
                            } else {
                                if (bc.isValidViewType(bD)) {
                                    bA = function() {
                                        bc.changeView(bD)
                                    };
                                    bg.push(bD);
                                    bB = bc.getViewButtonText(bD)
                                }
                            }
                            if (bA) {
                                bu = H(bl.themeButtonIcons, bD);
                                bw = H(bl.buttonIcons, bD);
                                bC = H(bl.defaultButtonText, bD);
                                bz = H(bl.buttonText, bD);
                                if (bB || bz) {
                                    bt = a2(bB || bz)
                                } else {
                                    if (bu && bl.theme) {
                                        bt = "<span class='ui-icon ui-icon-" + bu + "'></span>"
                                    } else {
                                        if (bw && !bl.theme) {
                                            bt = "<span class='fc-icon fc-icon-" + bw + "'></span>"
                                        } else {
                                            bt = a2(bC || bD)
                                        }
                                    }
                                }
                                bv = ["fc-" + bD + "-button", bh + "-button", bh + "-state-default"];
                                by = aK('<button type="button" class="' + bv.join(" ") + '">' + bt + "</button>").click(function() {
                                    if (!by.hasClass(bh + "-state-disabled")) {
                                        bA();
                                        if (by.hasClass(bh + "-state-active") || by.hasClass(bh + "-state-disabled")) {
                                            by.removeClass(bh + "-state-hover")
                                        }
                                    }
                                }).mousedown(function() {
                                    by.not("." + bh + "-state-active").not("." + bh + "-state-disabled").addClass(bh + "-state-down")
                                }).mouseup(function() {
                                    by.removeClass(bh + "-state-down")
                                }).hover(function() {
                                    by.not("." + bh + "-state-active").not("." + bh + "-state-disabled").addClass(bh + "-state-hover")
                                }, function() {
                                    by.removeClass(bh + "-state-hover").removeClass(bh + "-state-down")
                                });
                                bq = bq.add(by)
                            }
                        }
                    });
                    if (br) {
                        bq.first().addClass(bh + "-corner-left").end().last().addClass(bh + "-corner-right").end()
                    }
                    if (bq.length > 1) {
                        bs = aK("<div/>");
                        if (br) {
                            bs.addClass("fc-button-group")
                        }
                        bs.append(bq);
                        bn.append(bs)
                    } else {
                        bn.append(bq)
                    }
                })
            }
            return bn
        }

        function bd(bm) {
            a9.find("h2").text(bm)
        }

        function a7(bm) {
            a9.find(".fc-" + bm + "-button").addClass(bh + "-state-active")
        }

        function bi(bm) {
            a9.find(".fc-" + bm + "-button").removeClass(bh + "-state-active")
        }

        function bb(bm) {
            a9.find(".fc-" + bm + "-button").attr("disabled", "disabled").addClass(bh + "-state-disabled")
        }

        function be(bm) {
            a9.find(".fc-" + bm + "-button").removeAttr("disabled").removeClass(bh + "-state-disabled")
        }

        function ba() {
            return bg
        }
    }
    aY.sourceNormalizers = [];
    aY.sourceFetchers = [];
    var m = {
        dataType: "json",
        cache: false
    };
    var ak = 1;

    function o(by) {
        var bJ = this;
        bJ.isFetchNeeded = a7;
        bJ.fetchEvents = bM;
        bJ.addEventSource = bg;
        bJ.removeEventSource = ba;
        bJ.updateEvent = bE;
        bJ.renderEvent = bp;
        bJ.removeEvents = bK;
        bJ.clientEvents = bi;
        bJ.mutateEvent = a8;
        bJ.normalizeEventDateProps = bB;
        bJ.ensureVisibleEventRange = bv;
        var bC = bJ.trigger;
        var bA = bJ.getView;
        var bm = bJ.reportEvents;
        var bd = {
            events: []
        };
        var bG = [bd];
        var bj, bn;
        var bN = 0;
        var bt = 0;
        var bP = 0;
        var bl = [];
        aK.each((by.events ? [by.events] : []).concat(by.eventSources || []), function(bQ, bS) {
            var bR = bu(bS);
            if (bR) {
                bG.push(bR)
            }
        });

        function a7(bR, bQ) {
            return !bj || bR.clone().stripZone() < bj.clone().stripZone() || bQ.clone().stripZone() > bn.clone().stripZone()
        }

        function bM(bU, bR) {
            bj = bU;
            bn = bR;
            bl = [];
            var bT = ++bN;
            var bQ = bG.length;
            bt = bQ;
            for (var bS = 0; bS < bQ; bS++) {
                bq(bG[bS], bT)
            }
        }

        function bq(bR, bQ) {
            be(bR, function(bW) {
                var bV = aK.isArray(bR.events);
                var bU, bT;
                var bS;
                if (bQ == bN) {
                    if (bW) {
                        for (bU = 0; bU < bW.length; bU++) {
                            bT = bW[bU];
                            if (bV) {
                                bS = bT
                            } else {
                                bS = bc(bT, bR)
                            }
                            if (bS) {
                                bl.push.apply(bl, bO(bS))
                            }
                        }
                    }
                    bt--;
                    if (!bt) {
                        bm(bl)
                    }
                }
            })
        }

        function be(bQ, b1) {
            var bV;
            var bZ = aY.sourceFetchers;
            var bX;
            for (bV = 0; bV < bZ.length; bV++) {
                bX = bZ[bV].call(bJ, bQ, bj.clone(), bn.clone(), by.timezone, b1);
                if (bX === true) {
                    return
                } else {
                    if (typeof bX == "object") {
                        be(bX, b1);
                        return
                    }
                }
            }
            var b3 = bQ.events;
            if (b3) {
                if (aK.isFunction(b3)) {
                    bf();
                    b3.call(bJ, bj.clone(), bn.clone(), by.timezone, function(b5) {
                        b1(b5);
                        bb()
                    })
                } else {
                    if (aK.isArray(b3)) {
                        b1(b3)
                    } else {
                        b1()
                    }
                }
            } else {
                var bR = bQ.url;
                if (bR) {
                    var b2 = bQ.success;
                    var bY = bQ.error;
                    var bS = bQ.complete;
                    var b4;
                    if (aK.isFunction(bQ.data)) {
                        b4 = bQ.data()
                    } else {
                        b4 = bQ.data
                    }
                    var bU = aK.extend({}, b4 || {});
                    var bW = a4(bQ.startParam, by.startParam);
                    var bT = a4(bQ.endParam, by.endParam);
                    var b0 = a4(bQ.timezoneParam, by.timezoneParam);
                    if (bW) {
                        bU[bW] = bj.format()
                    }
                    if (bT) {
                        bU[bT] = bn.format()
                    }
                    if (by.timezone && by.timezone != "local") {
                        bU[b0] = by.timezone
                    }
                    bf();
                    aK.ajax(aK.extend({}, m, bQ, {
                        data: bU,
                        success: function(b6) {
                            b6 = b6 || [];
                            var b5 = N(b2, this, arguments);
                            if (aK.isArray(b5)) {
                                b6 = b5
                            }
                            b1(b6)
                        },
                        error: function() {
                            N(bY, this, arguments);
                            b1()
                        },
                        complete: function() {
                            N(bS, this, arguments);
                            bb()
                        }
                    }))
                } else {
                    b1()
                }
            }
        }

        function bg(bR) {
            var bQ = bu(bR);
            if (bQ) {
                bG.push(bQ);
                bt++;
                bq(bQ, bN)
            }
        }

        function bu(bT) {
            var bR = aY.sourceNormalizers;
            var bS;
            var bQ;
            if (aK.isFunction(bT) || aK.isArray(bT)) {
                bS = {
                    events: bT
                }
            } else {
                if (typeof bT === "string") {
                    bS = {
                        url: bT
                    }
                } else {
                    if (typeof bT === "object") {
                        bS = aK.extend({}, bT)
                    }
                }
            }
            if (bS) {
                if (bS.className) {
                    if (typeof bS.className === "string") {
                        bS.className = bS.className.split(/\s+/)
                    }
                } else {
                    bS.className = []
                }
                if (aK.isArray(bS.events)) {
                    bS.origArray = bS.events;
                    bS.events = aK.map(bS.events, function(bU) {
                        return bc(bU, bS)
                    })
                }
                for (bQ = 0; bQ < bR.length; bQ++) {
                    bR[bQ].call(bJ, bS)
                }
                return bS
            }
        }

        function ba(bQ) {
            bG = aK.grep(bG, function(bR) {
                return !bI(bR, bQ)
            });
            bl = aK.grep(bl, function(bR) {
                return !bI(bR.source, bQ)
            });
            bm(bl)
        }

        function bI(bR, bQ) {
            return bR && bQ && bk(bR) == bk(bQ)
        }

        function bk(bQ) {
            return ((typeof bQ === "object") ? (bQ.origArray || bQ.googleCalendarId || bQ.url || bQ.events) : null) || bQ
        }

        function bE(bQ) {
            bQ.start = bJ.moment(bQ.start);
            if (bQ.end) {
                bQ.end = bJ.moment(bQ.end)
            } else {
                bQ.end = null
            }
            a8(bQ, a9(bQ));
            bm(bl)
        }

        function a9(bR) {
            var bQ = {};
            aK.each(bR, function(bS, bT) {
                if (bH(bS)) {
                    if (bT !== undefined && aA(bT)) {
                        bQ[bS] = bT
                    }
                }
            });
            return bQ
        }

        function bH(bQ) {
            return !/^_|^(id|allDay|start|end)$/.test(bQ)
        }

        function bp(bU, bQ) {
            var bR = bc(bU);
            var bT;
            var bS, bV;
            if (bR) {
                bT = bO(bR);
                for (bS = 0; bS < bT.length; bS++) {
                    bV = bT[bS];
                    if (!bV.source) {
                        if (bQ) {
                            bd.events.push(bV);
                            bV.source = bd
                        }
                        bl.push(bV)
                    }
                }
                bm(bl);
                return bT
            }
            return []
        }

        function bK(bS) {
            var bR;
            var bQ;
            if (bS == null) {
                bS = function() {
                    return true
                }
            } else {
                if (!aK.isFunction(bS)) {
                    bR = bS + "";
                    bS = function(bT) {
                        return bT._id == bR
                    }
                }
            }
            bl = aK.grep(bl, bS, true);
            for (bQ = 0; bQ < bG.length; bQ++) {
                if (aK.isArray(bG[bQ].events)) {
                    bG[bQ].events = aK.grep(bG[bQ].events, bS, true)
                }
            }
            bm(bl)
        }

        function bi(bQ) {
            if (aK.isFunction(bQ)) {
                return aK.grep(bl, bQ)
            } else {
                if (bQ != null) {
                    bQ += "";
                    return aK.grep(bl, function(bR) {
                        return bR._id == bQ
                    })
                }
            }
            return bl
        }

        function bf() {
            if (!(bP++)) {
                bC("loading", null, true, bA())
            }
        }

        function bb() {
            if (!(--bP)) {
                bC("loading", null, false, bA())
            }
        }

        function bc(bR, bU) {
            var bS = {};
            var bV, bQ;
            var bT;
            if (by.eventDataTransform) {
                bR = by.eventDataTransform(bR)
            }
            if (bU && bU.eventDataTransform) {
                bR = bU.eventDataTransform(bR)
            }
            aK.extend(bS, bR);
            if (bU) {
                bS.source = bU
            }
            bS._id = bR._id || (bR.id === undefined ? "_fc" + ak++ : bR.id + "");
            if (bR.className) {
                if (typeof bR.className == "string") {
                    bS.className = bR.className.split(/\s+/)
                } else {
                    bS.className = bR.className
                }
            } else {
                bS.className = []
            }
            bV = bR.start || bR.date;
            bQ = bR.end;
            if (L(bV)) {
                bV = z.duration(bV)
            }
            if (L(bQ)) {
                bQ = z.duration(bQ)
            }
            if (bR.dow || z.isDuration(bV) || z.isDuration(bQ)) {
                bS.start = bV ? z.duration(bV) : null;
                bS.end = bQ ? z.duration(bQ) : null;
                bS._recurring = true
            } else {
                if (bV) {
                    bV = bJ.moment(bV);
                    if (!bV.isValid()) {
                        return false
                    }
                }
                if (bQ) {
                    bQ = bJ.moment(bQ);
                    if (!bQ.isValid()) {
                        bQ = null
                    }
                }
                bT = bR.allDay;
                if (bT === undefined) {
                    bT = a4(bU ? bU.allDayDefault : undefined, by.allDayDefault)
                }
                bw(bV, bQ, bT, bS)
            }
            return bS
        }

        function bw(bT, bQ, bS, bR) {
            bR.start = bT;
            bR.end = bQ;
            bR.allDay = bS;
            bB(bR);
            at(bR)
        }

        function bB(bQ) {
            if (bQ.allDay == null) {
                bQ.allDay = !(bQ.start.hasTime() || (bQ.end && bQ.end.hasTime()))
            }
            if (bQ.allDay) {
                bQ.start.stripTime();
                if (bQ.end) {
                    bQ.end.stripTime()
                }
            } else {
                if (!bQ.start.hasTime()) {
                    bQ.start = bJ.rezoneDate(bQ.start)
                }
                if (bQ.end && !bQ.end.hasTime()) {
                    bQ.end = bJ.rezoneDate(bQ.end)
                }
            }
            if (bQ.end && !bQ.end.isAfter(bQ.start)) {
                bQ.end = null
            }
            if (!bQ.end) {
                if (by.forceEventDuration) {
                    bQ.end = bJ.getDefaultEventEnd(bQ.allDay, bQ.start)
                } else {
                    bQ.end = null
                }
            }
        }

        function bv(bQ) {
            var bR;
            if (!bQ.end) {
                bR = bQ.allDay;
                if (bR == null) {
                    bR = !bQ.start.hasTime()
                }
                bQ = {
                    start: bQ.start,
                    end: bJ.getDefaultEventEnd(bR, bQ.start)
                }
            }
            return bQ
        }

        function bO(bX, b0, bT) {
            var b2 = [];
            var bZ;
            var b1;
            var bW;
            var bU;
            var bS, bY;
            var bR, bV;
            var bQ;
            b0 = b0 || bj;
            bT = bT || bn;
            if (bX) {
                if (bX._recurring) {
                    if ((b1 = bX.dow)) {
                        bZ = {};
                        for (bW = 0; bW < b1.length; bW++) {
                            bZ[b1[bW]] = true
                        }
                    }
                    bU = b0.clone().stripTime();
                    while (bU.isBefore(bT)) {
                        if (!bZ || bZ[bU.day()]) {
                            bS = bX.start;
                            bY = bX.end;
                            bR = bU.clone();
                            bV = null;
                            if (bS) {
                                bR = bR.time(bS)
                            }
                            if (bY) {
                                bV = bU.clone().time(bY)
                            }
                            bQ = aK.extend({}, bX);
                            bw(bR, bV, !bS && !bY, bQ);
                            b2.push(bQ)
                        }
                        bU.add(1, "days")
                    }
                } else {
                    b2.push(bX)
                }
            }
            return b2
        }

        function a8(bU, bT) {
            var bS = {};
            var bW;
            var bV;
            var bR;
            var bQ;
            bT = bT || {};
            if (!bT.start) {
                bT.start = bU.start.clone()
            }
            if (bT.end === undefined) {
                bT.end = bU.end ? bU.end.clone() : null
            }
            if (bT.allDay == null) {
                bT.allDay = bU.allDay
            }
            bB(bT);
            bW = bU._end !== null && bT.end === null;
            if (bT.allDay) {
                bV = U(bT.start, bU._start)
            } else {
                bV = t(bT.start, bU._start)
            }
            if (!bW && bT.end) {
                bR = t(bT.end, bT.start).subtract(t(bU._end || bJ.getDefaultEventEnd(bU._allDay, bU._start), bU._start))
            }
            aK.each(bT, function(bX, bY) {
                if (bH(bX)) {
                    if (bY !== undefined) {
                        bS[bX] = bY
                    }
                }
            });
            bQ = bz(bi(bU._id), bW, bT.allDay, bV, bR, bS);
            return {
                dateDelta: bV,
                durationDelta: bR,
                undo: bQ
            }
        }

        function bz(bU, bX, bW, bV, bS, bT) {
            var bR = bJ.getIsAmbigTimezone();
            var bQ = [];
            if (bV && !bV.valueOf()) {
                bV = null
            }
            if (bS && !bS.valueOf()) {
                bS = null
            }
            aK.each(bU, function(bZ, b1) {
                var bY;
                var b0;
                bY = {
                    start: b1.start.clone(),
                    end: b1.end ? b1.end.clone() : null,
                    allDay: b1.allDay
                };
                aK.each(bT, function(b2) {
                    bY[b2] = b1[b2]
                });
                b0 = {
                    start: b1._start,
                    end: b1._end,
                    allDay: b1._allDay
                };
                if (bX) {
                    b0.end = null
                }
                b0.allDay = bW;
                bB(b0);
                if (bV) {
                    b0.start.add(bV);
                    if (b0.end) {
                        b0.end.add(bV)
                    }
                }
                if (bS) {
                    if (!b0.end) {
                        b0.end = bJ.getDefaultEventEnd(b0.allDay, b0.start)
                    }
                    b0.end.add(bS)
                }
                if (bR && !b0.allDay && (bV || bS)) {
                    b0.start.stripZone();
                    if (b0.end) {
                        b0.end.stripZone()
                    }
                }
                aK.extend(b1, bT, b0);
                at(b1);
                bQ.push(function() {
                    aK.extend(b1, bY);
                    at(b1)
                })
            });
            return function() {
                for (var bY = 0; bY < bQ.length; bY++) {
                    bQ[bY]()
                }
            }
        }
        bJ.getBusinessHoursEvents = bo;

        function bo() {
            var bS = by.businessHours;
            var bT = {
                className: "fc-nonbusiness",
                start: "09:00",
                end: "17:00",
                dow: [1, 2, 3, 4, 5],
                rendering: "inverse-background"
            };
            var bQ = bJ.getView();
            var bR;
            if (bS) {
                if (typeof bS === "object") {
                    bR = aK.extend({}, bT, bS)
                } else {
                    bR = bT
                }
            }
            if (bR) {
                return bO(bc(bR), bQ.start, bQ.end)
            }
            return []
        }
        bJ.isEventRangeAllowed = bs;
        bJ.isSelectionRangeAllowed = br;
        bJ.isExternalDropRangeAllowed = bF;

        function bs(bR, bS) {
            var bT = bS.source || {};
            var bU = a4(bS.constraint, bT.constraint, by.eventConstraint);
            var bQ = a4(bS.overlap, bT.overlap, by.eventOverlap);
            bR = bv(bR);
            return bx(bR, bU, bQ, bS)
        }

        function br(bQ) {
            return bx(bQ, by.selectConstraint, by.selectOverlap)
        }

        function bF(bQ, bT) {
            var bR;
            var bS;
            if (bT) {
                bR = aK.extend({}, bT, bQ);
                bS = bO(bc(bR))[0]
            }
            if (bS) {
                return bs(bQ, bS)
            } else {
                bQ = bv(bQ);
                return br(bQ)
            }
        }

        function bx(bW, bS, bY, bR) {
            var bQ;
            var bT;
            var bV, bX;
            var bU;
            bW = {
                start: bW.start.clone().stripZone(),
                end: bW.end.clone().stripZone()
            };
            if (bS != null) {
                bQ = bD(bS);
                bT = false;
                for (bV = 0; bV < bQ.length; bV++) {
                    if (bh(bQ[bV], bW)) {
                        bT = true;
                        break
                    }
                }
                if (!bT) {
                    return false
                }
            }
            for (bV = 0; bV < bl.length; bV++) {
                bX = bl[bV];
                if (bR && bR._id === bX._id) {
                    continue
                }
                if (bL(bX, bW)) {
                    if (bY === false) {
                        return false
                    } else {
                        if (typeof bY === "function" && !bY(bX, bR)) {
                            return false
                        }
                    }
                    if (bR) {
                        bU = a4(bX.overlap, (bX.source || {}).overlap);
                        if (bU === false) {
                            return false
                        }
                        if (typeof bU === "function" && !bU(bR, bX)) {
                            return false
                        }
                    }
                }
            }
            return true
        }

        function bD(bQ) {
            if (bQ === "businessHours") {
                return bo()
            }
            if (typeof bQ === "object") {
                return bO(bc(bQ))
            }
            return bi(bQ)
        }

        function bh(bT, bS) {
            var bR = bT.start.clone().stripZone();
            var bQ = bJ.getEventEnd(bT).stripZone();
            return bS.start >= bR && bS.end <= bQ
        }

        function bL(bT, bS) {
            var bR = bT.start.clone().stripZone();
            var bQ = bJ.getEventEnd(bT).stripZone();
            return bS.start < bQ && bS.end > bR
        }
    }

    function at(a7) {
        a7._allDay = a7.allDay;
        a7._start = a7.start.clone();
        a7._end = a7.end ? a7.end.clone() : null
    }
    var x = ax.basic = a1.extend({
        dayGrid: null,
        dayNumbersVisible: false,
        weekNumbersVisible: false,
        weekNumberWidth: null,
        headRowEl: null,
        initialize: function() {
            this.dayGrid = new aS(this);
            this.coordMap = this.dayGrid.coordMap
        },
        setRange: function(a7) {
            a1.prototype.setRange.call(this, a7);
            this.dayGrid.breakOnWeeks = /year|month|week/.test(this.intervalUnit);
            this.dayGrid.setRange(a7)
        },
        computeRange: function(a8) {
            var a7 = a1.prototype.computeRange.call(this, a8);
            if (/year|month/.test(a7.intervalUnit)) {
                a7.start.startOf("week");
                a7.start = this.skipHiddenDays(a7.start);
                if (a7.end.weekday()) {
                    a7.end.add(1, "week").startOf("week");
                    a7.end = this.skipHiddenDays(a7.end, -1, true)
                }
            }
            return a7
        },
        render: function() {
            this.dayNumbersVisible = this.dayGrid.rowCnt > 1;
            this.weekNumbersVisible = this.opt("weekNumbers");
            this.dayGrid.numbersVisible = this.dayNumbersVisible || this.weekNumbersVisible;
            this.el.addClass("fc-basic-view").html(this.renderHtml());
            this.headRowEl = this.el.find("thead .fc-row");
            this.scrollerEl = this.el.find(".fc-day-grid-container");
            this.dayGrid.coordMap.containerEl = this.scrollerEl;
            this.dayGrid.el = this.el.find(".fc-day-grid");
            this.dayGrid.render(this.hasRigidRows())
        },
        destroy: function() {
            this.dayGrid.destroy();
            a1.prototype.destroy.call(this)
        },
        renderHtml: function() {
            return '<table><thead><tr><td class="' + this.widgetHeaderClass + '">' + this.dayGrid.headHtml() + '</td></tr></thead><tbody><tr><td class="' + this.widgetContentClass + '"><div class="fc-day-grid-container"><div class="fc-day-grid"/></div></td></tr></tbody></table>'
        },
        headIntroHtml: function() {
            if (this.weekNumbersVisible) {
                return '<th class="fc-week-number ' + this.widgetHeaderClass + '" ' + this.weekNumberStyleAttr() + "><span>" + a2(this.opt("weekNumberTitle")) + "</span></th>"
            }
        },
        numberIntroHtml: function(a7) {
            if (this.weekNumbersVisible) {
                return '<td class="fc-week-number" ' + this.weekNumberStyleAttr() + "><span>" + this.calendar.calculateWeekNumber(this.dayGrid.getCell(a7, 0).start) + "</span></td>"
            }
        },
        dayIntroHtml: function() {
            if (this.weekNumbersVisible) {
                return '<td class="fc-week-number ' + this.widgetContentClass + '" ' + this.weekNumberStyleAttr() + "></td>"
            }
        },
        introHtml: function() {
            if (this.weekNumbersVisible) {
                return '<td class="fc-week-number" ' + this.weekNumberStyleAttr() + "></td>"
            }
        },
        numberCellHtml: function(a7) {
            var a8 = a7.start;
            var a9;
            if (!this.dayNumbersVisible) {
                return "<td/>"
            }
            a9 = this.dayGrid.getDayClasses(a8);
            a9.unshift("fc-day-number");
            return '<td class="' + a9.join(" ") + '" data-date="' + a8.format() + '">' + a8.date() + "</td>"
        },
        weekNumberStyleAttr: function() {
            if (this.weekNumberWidth !== null) {
                return 'style="width:' + this.weekNumberWidth + 'px"'
            }
            return ""
        },
        hasRigidRows: function() {
            var a7 = this.opt("eventLimit");
            return a7 && typeof a7 !== "number"
        },
        updateWidth: function() {
            if (this.weekNumbersVisible) {
                this.weekNumberWidth = J(this.el.find(".fc-week-number"))
            }
        },
        setHeight: function(a9, a7) {
            var a8 = this.opt("eventLimit");
            var ba;
            r(this.scrollerEl);
            al(this.headRowEl);
            this.dayGrid.destroySegPopover();
            if (a8 && typeof a8 === "number") {
                this.dayGrid.limitRows(a8)
            }
            ba = this.computeScrollerHeight(a9);
            this.setGridHeight(ba, a7);
            if (a8 && typeof a8 !== "number") {
                this.dayGrid.limitRows(a8)
            }
            if (!a7 && aX(this.scrollerEl, ba)) {
                aW(this.headRowEl, j(this.scrollerEl));
                ba = this.computeScrollerHeight(a9);
                this.scrollerEl.height(ba);
                this.restoreScroll()
            }
        },
        setGridHeight: function(a8, a7) {
            if (a7) {
                c(this.dayGrid.rowEls)
            } else {
                G(this.dayGrid.rowEls, a8, true)
            }
        },
        renderEvents: function(a7) {
            this.dayGrid.renderEvents(a7);
            this.updateHeight()
        },
        getEventSegs: function() {
            return this.dayGrid.getEventSegs()
        },
        destroyEvents: function() {
            this.recordScroll();
            this.dayGrid.destroyEvents()
        },
        renderDrag: function(a8, a7) {
            return this.dayGrid.renderDrag(a8, a7)
        },
        destroyDrag: function() {
            this.dayGrid.destroyDrag()
        },
        renderSelection: function(a7) {
            this.dayGrid.renderSelection(a7)
        },
        destroySelection: function() {
            this.dayGrid.destroySelection()
        }
    });
    aC({
        fixedWeekCount: true
    });
    var av = ax.month = x.extend({
        computeRange: function(a8) {
            var a7 = x.prototype.computeRange.call(this, a8);
            if (this.isFixedWeeks()) {
                a7.end.add(6 - a7.end.diff(a7.start, "weeks"), "weeks")
            }
            return a7
        },
        setGridHeight: function(a8, a7) {
            a7 = a7 || this.opt("weekMode") === "variable";
            if (a7) {
                a8 *= this.rowCnt / 6
            }
            G(this.dayGrid.rowEls, a8, !a7)
        },
        isFixedWeeks: function() {
            var a7 = this.opt("weekMode");
            if (a7) {
                return a7 === "fixed"
            }
            return this.opt("fixedWeekCount")
        }
    });
    av.duration = {
        months: 1
    };
    ax.basicWeek = {
        type: "basic",
        duration: {
            weeks: 1
        }
    };
    ax.basicDay = {
        type: "basic",
        duration: {
            days: 1
        }
    };
    aC({
        allDaySlot: true,
        allDayText: "all-day",
        scrollTime: "06:00:00",
        slotDuration: "00:30:00",
        minTime: "00:00:00",
        maxTime: "24:00:00",
        slotEventOverlap: true
    });
    var a = 5;
    ax.agenda = a1.extend({
        timeGrid: null,
        dayGrid: null,
        axisWidth: null,
        noScrollRowEls: null,
        bottomRuleEl: null,
        bottomRuleHeight: null,
        initialize: function() {
            this.timeGrid = new h(this);
            if (this.opt("allDaySlot")) {
                this.dayGrid = new aS(this);
                this.coordMap = new D([this.dayGrid.coordMap, this.timeGrid.coordMap])
            } else {
                this.coordMap = this.timeGrid.coordMap
            }
        },
        setRange: function(a7) {
            a1.prototype.setRange.call(this, a7);
            this.timeGrid.setRange(a7);
            if (this.dayGrid) {
                this.dayGrid.setRange(a7)
            }
        },
        render: function() {
            this.el.addClass("fc-agenda-view").html(this.renderHtml());
            this.scrollerEl = this.el.find(".fc-time-grid-container");
            this.timeGrid.coordMap.containerEl = this.scrollerEl;
            this.timeGrid.el = this.el.find(".fc-time-grid");
            this.timeGrid.render();
            this.bottomRuleEl = aK('<hr class="' + this.widgetHeaderClass + '"/>').appendTo(this.timeGrid.el);
            if (this.dayGrid) {
                this.dayGrid.el = this.el.find(".fc-day-grid");
                this.dayGrid.render();
                this.dayGrid.bottomCoordPadding = this.dayGrid.el.next("hr").outerHeight()
            }
            this.noScrollRowEls = this.el.find(".fc-row:not(.fc-scroller *)")
        },
        destroy: function() {
            this.timeGrid.destroy();
            if (this.dayGrid) {
                this.dayGrid.destroy()
            }
            a1.prototype.destroy.call(this)
        },
        renderHtml: function() {
            return '<table><thead><tr><td class="' + this.widgetHeaderClass + '">' + this.timeGrid.headHtml() + '</td></tr></thead><tbody><tr><td class="' + this.widgetContentClass + '">' + (this.dayGrid ? '<div class="fc-day-grid"/><hr class="' + this.widgetHeaderClass + '"/>' : "") + '<div class="fc-time-grid-container"><div class="fc-time-grid"/></div></td></tr></tbody></table>'
        },
        headIntroHtml: function() {
            var a9;
            var a8;
            var a7;
            var ba;
            if (this.opt("weekNumbers")) {
                a9 = this.timeGrid.getCell(0).start;
                a8 = this.calendar.calculateWeekNumber(a9);
                a7 = this.opt("weekNumberTitle");
                if (this.opt("isRTL")) {
                    ba = a8 + a7
                } else {
                    ba = a7 + a8
                }
                return '<th class="fc-axis fc-week-number ' + this.widgetHeaderClass + '" ' + this.axisStyleAttr() + "><span>" + a2(ba) + "</span></th>"
            } else {
                return '<th class="fc-axis ' + this.widgetHeaderClass + '" ' + this.axisStyleAttr() + "></th>"
            }
        },
        dayIntroHtml: function() {
            return '<td class="fc-axis ' + this.widgetContentClass + '" ' + this.axisStyleAttr() + "><span>" + (this.opt("allDayHtml") || a2(this.opt("allDayText"))) + "</span></td>"
        },
        slotBgIntroHtml: function() {
            return '<td class="fc-axis ' + this.widgetContentClass + '" ' + this.axisStyleAttr() + "></td>"
        },
        introHtml: function() {
            return '<td class="fc-axis" ' + this.axisStyleAttr() + "></td>"
        },
        axisStyleAttr: function() {
            if (this.axisWidth !== null) {
                return 'style="width:' + this.axisWidth + 'px"'
            }
            return ""
        },
        updateSize: function(a7) {
            if (a7) {
                this.timeGrid.resize()
            }
            a1.prototype.updateSize.call(this, a7)
        },
        updateWidth: function() {
            this.axisWidth = J(this.el.find(".fc-axis"))
        },
        setHeight: function(a9, a7) {
            var a8;
            var ba;
            if (this.bottomRuleHeight === null) {
                this.bottomRuleHeight = this.bottomRuleEl.outerHeight()
            }
            this.bottomRuleEl.hide();
            this.scrollerEl.css("overflow", "");
            r(this.scrollerEl);
            al(this.noScrollRowEls);
            if (this.dayGrid) {
                this.dayGrid.destroySegPopover();
                a8 = this.opt("eventLimit");
                if (a8 && typeof a8 !== "number") {
                    a8 = a
                }
                if (a8) {
                    this.dayGrid.limitRows(a8)
                }
            }
            if (!a7) {
                ba = this.computeScrollerHeight(a9);
                if (aX(this.scrollerEl, ba)) {
                    aW(this.noScrollRowEls, j(this.scrollerEl));
                    ba = this.computeScrollerHeight(a9);
                    this.scrollerEl.height(ba);
                    this.restoreScroll()
                } else {
                    this.scrollerEl.height(ba).css("overflow", "hidden");
                    this.bottomRuleEl.show()
                }
            }
        },
        initializeScroll: function() {
            var ba = this;
            var a9 = z.duration(this.opt("scrollTime"));
            var a8 = this.timeGrid.computeTimeTop(a9);
            a8 = Math.ceil(a8);
            if (a8) {
                a8++
            }

            function a7() {
                ba.scrollerEl.scrollTop(a8)
            }
            a7();
            setTimeout(a7, 0)
        },
        renderEvents: function(a8) {
            var bb = [];
            var a9 = [];
            var bc = [];
            var ba;
            var a7;
            for (a7 = 0; a7 < a8.length; a7++) {
                if (a8[a7].allDay) {
                    bb.push(a8[a7])
                } else {
                    a9.push(a8[a7])
                }
            }
            ba = this.timeGrid.renderEvents(a9);
            if (this.dayGrid) {
                bc = this.dayGrid.renderEvents(bb)
            }
            this.updateHeight()
        },
        getEventSegs: function() {
            return this.timeGrid.getEventSegs().concat(this.dayGrid ? this.dayGrid.getEventSegs() : [])
        },
        destroyEvents: function() {
            this.recordScroll();
            this.timeGrid.destroyEvents();
            if (this.dayGrid) {
                this.dayGrid.destroyEvents()
            }
        },
        renderDrag: function(a8, a7) {
            if (a8.start.hasTime()) {
                return this.timeGrid.renderDrag(a8, a7)
            } else {
                if (this.dayGrid) {
                    return this.dayGrid.renderDrag(a8, a7)
                }
            }
        },
        destroyDrag: function() {
            this.timeGrid.destroyDrag();
            if (this.dayGrid) {
                this.dayGrid.destroyDrag()
            }
        },
        renderSelection: function(a7) {
            if (a7.start.hasTime() || a7.end.hasTime()) {
                this.timeGrid.renderSelection(a7)
            } else {
                if (this.dayGrid) {
                    this.dayGrid.renderSelection(a7)
                }
            }
        },
        destroySelection: function() {
            this.timeGrid.destroySelection();
            if (this.dayGrid) {
                this.dayGrid.destroySelection()
            }
        }
    });
    ax.agendaWeek = {
        type: "agenda",
        duration: {
            weeks: 1
        }
    };
    ax.agendaDay = {
        type: "agenda",
        duration: {
            days: 1
        }
    }
});
PrimeFaces.widget.Schedule = PrimeFaces.widget.DeferredWidget.extend({
    init: function(a) {
        this._super(a);
        this.cfg.formId = this.jq.closest("form").attr("id");
        this.cfg.theme = true;
        this.jqc = $(this.jqId + "_container");
        this.viewNameState = $(this.jqId + "_view");
        if (this.cfg.defaultDate) {
            this.cfg.defaultDate = moment(this.cfg.defaultDate)
        }
        this.setupEventSource();
        this.configureLocale();
        if (this.cfg.tooltip) {
            this.tip = $('<div class="ui-tooltip ui-widget ui-widget-content ui-shadow ui-corner-all"></div>').appendTo(this.jq)
        }
        this.setupEventHandlers();
        if (this.cfg.extender) {
            this.cfg.extender.call(this)
        }
        this.renderDeferred()
    },
    _render: function() {
        this.jqc.fullCalendar(this.cfg);
        this.bindViewChangeListener()
    },
    configureLocale: function() {
        var a = PrimeFaces.locales[this.cfg.locale];
        if (a) {
            this.cfg.firstDay = a.firstDay;
            this.cfg.monthNames = a.monthNames;
            this.cfg.monthNamesShort = a.monthNamesShort;
            this.cfg.dayNames = a.dayNames;
            this.cfg.dayNamesShort = a.dayNamesShort;
            this.cfg.buttonText = {
                today: a.currentText,
                month: a.month,
                week: a.week,
                day: a.day
            };
            this.cfg.allDayText = a.allDayText;
            if (a.eventLimitText) {
                this.cfg.eventLimitText = a.eventLimitText
            }
        }
    },
    setupEventHandlers: function() {
        var a = this;
        this.cfg.dayClick = function(b, d, c) {
            if (a.cfg.behaviors) {
                var f = a.cfg.behaviors.dateSelect;
                if (f) {
                    var e = {
                        params: [{
                            name: a.id + "_selectedDate",
                            value: b.valueOf() - b.zone() * 60000
                        }]
                    };
                    f.call(a, e)
                }
            }
        };
        /*
        this.cfg.viewRender = function(view){
        	console.log('viewRender');
        	 curdate = new Date();
             viewdate = new Date(view.start);

             // PREV - force minimum display month to current month
             if (new Date(viewdate.getFullYear(), viewdate.getMonth() + 1, 1).getTime() <= 
                 new Date(curdate.getFullYear(), curdate.getMonth(), 1).getTime()){
                 $('.fc-prev-button').prop('disabled', true);
                 $('.fc-prev-button').css('opacity', 0.5);
             } else {
                 $('.fc-prev-button').prop('disabled', false);
                 $('.fc-prev-button').css('opacity', 1);
             }

             // NEXT - force max display month to a year from current month
             if (new Date(viewdate.getFullYear(), viewdate.getMonth() + 1).getTime() >= 
                 new Date(curdate.getFullYear() + 1, curdate.getMonth() + 1).getTime()){
                 $('.fc-next-button').prop('disabled', true);
                 $('.fc-next-button').css('opacity', 0.5);
             } else {
                 $('.fc-next-button').prop('disabled', false);
                 $('.fc-next-button').css('opacity', 1);
             }
        };
        */
        this.cfg.eventClick = function(f, c, b) {
            if (a.cfg.behaviors) {
                var e = a.cfg.behaviors.eventSelect;
                if (e) {
                    var d = {
                        params: [{
                            name: a.id + "_selectedEventId",
                            value: f.id
                        }]
                    };
                    e.call(a, d)
                }
            }
        };
        this.cfg.eventDrop = function(i, h, f, d, g, c) {
            if (a.cfg.behaviors) {
                var b = a.cfg.behaviors.eventMove;
                if (b) {
                    var e = {
                        params: [{
                            name: a.id + "_movedEventId",
                            value: i.id
                        }, {
                            name: a.id + "_dayDelta",
                            value: h.days()
                        }, {
                            name: a.id + "_minuteDelta",
                            value: (h._milliseconds / 60000)
                        }]
                    };
                    b.call(a, e)
                }
            }
        };
        this.cfg.eventResize = function(i, h, f, c, g, b) {
            if (a.cfg.behaviors) {
                var d = a.cfg.behaviors.eventResize;
                if (d) {
                    var e = {
                        params: [{
                            name: a.id + "_resizedEventId",
                            value: i.id
                        }, {
                            name: a.id + "_dayDelta",
                            value: h.days()
                        }, {
                            name: a.id + "_minuteDelta",
                            value: (h._milliseconds / 60000)
                        }]
                    };
                    d.call(a, e)
                }
            }
        };
        if (this.cfg.tooltip) {
            this.cfg.eventMouseover = function(d, c, b) {
                if (d.description) {
                    a.tipTimeout = setTimeout(function() {
                        a.tip.css({
                            left: c.pageX,
                            top: c.pageY + 15,
                            "z-index": ++PrimeFaces.zindex
                        }).html(d.description).show()
                    }, 150)
                }
            };
            this.cfg.eventMouseout = function(d, c, b) {
                if (a.tipTimeout) {
                    clearTimeout(a.tipTimeout)
                }
                if (a.tip.is(":visible")) {
                    a.tip.hide();
                    a.tip.text("")
                }
            }
        }
    },
    setupEventSource: function() {
        var a = this,
            b = moment().zone() * 60000;
        this.cfg.events = function(g, c, e, f) {
            var d = {
                source: a.id,
                process: a.id,
                update: a.id,
                formId: a.cfg.formId,
                params: [{
                    name: a.id + "_start",
                    value: g.valueOf() + b
                }, {
                    name: a.id + "_end",
                    value: c.valueOf() + b
                }],
                onsuccess: function(j, h, i) {
                    PrimeFaces.ajax.Response.handle(j, h, i, {
                        widget: a,
                        handle: function(k) {
                            f($.parseJSON(k).events)
                        }
                    });
                    return true
                }
            };
            PrimeFaces.ajax.Request.handle(d)
        }
    },
    update: function() {
        this.jqc.fullCalendar("refetchEvents")
    },
    bindViewChangeListener: function() {
        var a = this.jqc.find("> .fc-toolbar button:not(.fc-prev-button,.fc-next-button,.fc-today-button)"),
            b = this;
        a.each(function(d) {
            var e = a.eq(d),
                f = e.attr("class").split(" ");
            for (var d = 0; d < f.length; d++) {
                var c = f[d].split("-");
                if (c.length === 3) {
                    e.data("view", c[1]);
                    break
                }
            }
        });
        a.on("click.schedule", function() {
            var c = $(this).data("view");
            b.viewNameState.val(c);
            if (b.cfg.behaviors) {
                var d = b.cfg.behaviors.viewChange;
                if (d) {
                    d.call(b)
                }
            }
        })
    }
});

function isFeriado(data) {
	var ano = new Date().getFullYear();
	var feriados = [];
	feriados.push(new Date(ano, 0, 1, 0, 0, 0));
	feriados.push(new Date(ano, 3, 21, 0, 0, 0));
	feriados.push(new Date(ano, 4, 1, 0, 0, 0));
	feriados.push(new Date(ano, 8, 7, 0, 0, 0));
	feriados.push(new Date(ano, 9, 12, 0, 0, 0));
	feriados.push(new Date(ano, 10, 2, 0, 0, 0));
	feriados.push(new Date(ano, 10, 15, 0, 0, 0));
	feriados.push(new Date(ano, 11, 25, 0, 0, 0));
	//remover
	//feriados.push(new Date(ano, 8, 17, 0, 0, 0));
	
	var feriado = false;
	for (var i = 0; i < feriados.length; i++) {
		if ( eval(feriados[i].getTime() == semHora(data).getTime()) ) {
			feriado = true;
			break;
		}
	}
	return feriado;
}

function semHora(data) {
    data.setHours(0, 0, 0, 0, 0);
    return data;
}