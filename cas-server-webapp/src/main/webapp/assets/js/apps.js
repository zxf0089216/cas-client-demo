/*
    ----------------------------
        APPS CONTENT TABLE
    ----------------------------
    
    <!-- ======== GLOBAL SCRIPT SETTING ======== -->
    01. Handle Scrollbar 
    02. Handle Sidebar - Menu
    03. Handle Sidebar - Mobile View Toggle
    04. Handle Sidebar - Minify / Expand
    
    <!-- ======== Added in V1.6 ======== -->
    05. Handle IE Full Height Page Compatibility - added in V1.6
    
    <!-- ======== Added in V1.7 ======== -->
    06. Handle Mobile Sidebar Scrolling Feature - added in V1.7
    
    <!-- ======== Added in V1.9 ======== -->
    07. Handle Clear Sidebar Selection & Hide Mobile Menu - added in V1.9

    <!-- ======== APPLICATION SETTING ======== -->
    Application Controller
*/


/* 01. Handle Scrollbar
------------------------------------------------ */
var handleSlimScroll = function() {
    "use strict";
    $('[data-scrollbar=true]').each( function() {
        generateSlimScroll($(this));
    });
};
var generateSlimScroll = function(element) {
    if ($(element).attr('data-init')) {
        return;
    }
    var dataHeight = $(element).attr('data-height');
        dataHeight = (!dataHeight) ? $(element).height() : dataHeight;
    
    var scrollBarOption = {
        height: dataHeight, 
        alwaysVisible: true
    };
    if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
        $(element).css('height', dataHeight);
        $(element).css('overflow-x','scroll');
    } else {
        $(element).slimScroll(scrollBarOption);
    }
    $(element).attr('data-init', true);
};


/* 02. Handle Sidebar - Menu
------------------------------------------------ */
var handleSidebarMenu = function() {
    "use strict";
    $('.sidebar .nav > .has-sub > a').click(function() {
        var target = $(this).next('.sub-menu');
        var otherMenu = '.sidebar .nav > li.has-sub > .sub-menu';
    
        if ($('.page-sidebar-minified').length === 0) {
            $(otherMenu).not(target).slideUp(250, function() {
                $(this).closest('li').removeClass('expand');
            });
            $(target).slideToggle(250, function() {
                var targetLi = $(this).closest('li');
                if ($(targetLi).hasClass('expand')) {
                    $(targetLi).removeClass('expand');
                } else {
                    $(targetLi).addClass('expand');
                }
            });
        }
    });
    $('.sidebar .nav > .has-sub .sub-menu li.has-sub > a').click(function() {
        if ($('.page-sidebar-minified').length === 0) {
            var target = $(this).next('.sub-menu');
            $(target).slideToggle(250);
        }
    });
};


/* 03. Handle Sidebar - Mobile View Toggle
------------------------------------------------ */
var handleMobileSidebarToggle = function() {
    var sidebarProgress = false;
    $('.sidebar').bind('click touchstart', function(e) {
        if ($(e.target).closest('.sidebar').length !== 0) {
            sidebarProgress = true;
        } else {
            sidebarProgress = false;
            e.stopPropagation();
        }
    });
    
    $(document).bind('click touchstart', function(e) {
        if ($(e.target).closest('.sidebar').length === 0) {
            sidebarProgress = false;
        }
        if (!e.isPropagationStopped() && sidebarProgress !== true) {
            if ($('#page-container').hasClass('page-sidebar-toggled')) {
                sidebarProgress = true;
                $('#page-container').removeClass('page-sidebar-toggled');
            }
            if ($(window).width() <= 767) {
                if ($('#page-container').hasClass('page-right-sidebar-toggled')) {
                    sidebarProgress = true;
                    $('#page-container').removeClass('page-right-sidebar-toggled');
                }
            }
        }
    });
    
    $('[data-click=right-sidebar-toggled]').click(function(e) {
        e.stopPropagation();
        var targetContainer = '#page-container';
        var targetClass = 'page-right-sidebar-collapsed';
            targetClass = ($(window).width() < 979) ? 'page-right-sidebar-toggled' : targetClass;
        if ($(targetContainer).hasClass(targetClass)) {
            $(targetContainer).removeClass(targetClass);
        } else if (sidebarProgress !== true) {
            $(targetContainer).addClass(targetClass);
        } else {
            sidebarProgress = false;
        }
        if ($(window).width() < 480) {
            $('#page-container').removeClass('page-sidebar-toggled');
        }
        $(window).trigger('resize');
    });
    
    $('[data-click=sidebar-toggled]').click(function(e) {
        e.stopPropagation();
        var sidebarClass = 'page-sidebar-toggled';
        var targetContainer = '#page-container';

        if ($(targetContainer).hasClass(sidebarClass)) {
            $(targetContainer).removeClass(sidebarClass);
        } else if (sidebarProgress !== true) {
            $(targetContainer).addClass(sidebarClass);
        } else {
            sidebarProgress = false;
        }
        if ($(window).width() < 480) {
            $('#page-container').removeClass('page-right-sidebar-toggled');
        }
    });
};


/* 04. Handle Sidebar - Minify / Expand
------------------------------------------------ */
var handleSidebarMinify = function() {
    $('[data-click=sidebar-minify]').click(function(e) {
        e.preventDefault();
        var sidebarClass = 'page-sidebar-minified';
        var targetContainer = '#page-container';
        $('#sidebar [data-scrollbar="true"]').css('margin-top','0');
        $('#sidebar [data-scrollbar="true"]').removeAttr('data-init');
        $('#sidebar [data-scrollbar=true]').stop();
        if ($(targetContainer).hasClass(sidebarClass)) {
            $(targetContainer).removeClass(sidebarClass);
            if ($(targetContainer).hasClass('page-sidebar-fixed')) {
                if ($('#sidebar .slimScrollDiv').length !== 0) {
                    $('#sidebar [data-scrollbar="true"]').slimScroll({destroy: true});
                    $('#sidebar [data-scrollbar="true"]').removeAttr('style');
                }
                generateSlimScroll($('#sidebar [data-scrollbar="true"]'));
                $('#sidebar [data-scrollbar=true]').trigger('mouseover');
            } else if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
                if ($('#sidebar .slimScrollDiv').length !== 0) {
                    $('#sidebar [data-scrollbar="true"]').slimScroll({destroy: true});
                    $('#sidebar [data-scrollbar="true"]').removeAttr('style');
                }
                generateSlimScroll($('#sidebar [data-scrollbar="true"]'));
            }
        } else {
            $(targetContainer).addClass(sidebarClass);
    
            if(!/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
                if ($(targetContainer).hasClass('page-sidebar-fixed')) {
                    $('#sidebar [data-scrollbar="true"]').slimScroll({destroy: true});
                    $('#sidebar [data-scrollbar="true"]').removeAttr('style');
                }
                $('#sidebar [data-scrollbar=true]').trigger('mouseover');
            } else {
                $('#sidebar [data-scrollbar="true"]').css('margin-top','0');
                $('#sidebar [data-scrollbar="true"]').css('overflow', 'visible');
            }
        }
        $(window).trigger('resize');
    });
};


/* 05. Handle IE Full Height Page Compatibility - added in V1.6
------------------------------------------------ */
var handleIEFullHeightContent = function() {
    var userAgent = window.navigator.userAgent;
    var msie = userAgent.indexOf("MSIE ");

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) {
        $('.vertical-box-row [data-scrollbar="true"][data-height="100%"]').each(function() {
            var targetRow = $(this).closest('.vertical-box-row');
            var targetHeight = $(targetRow).height();
            $(targetRow).find('.vertical-box-cell').height(targetHeight);
        });
    }
};


/* 06. Handle Mobile Sidebar Scrolling Feature - added in V1.7
------------------------------------------------ */
var handleMobileSidebar = function() {
    "use strict";
    if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
        if ($('#page-container').hasClass('page-sidebar-minified')) {
            $('#sidebar [data-scrollbar="true"]').css('overflow', 'visible');
            $('.page-sidebar-minified #sidebar [data-scrollbar="true"]').slimScroll({destroy: true});
            $('.page-sidebar-minified #sidebar [data-scrollbar="true"]').removeAttr('style');
            $('.page-sidebar-minified #sidebar [data-scrollbar=true]').trigger('mouseover');
        }
    }

    var oriTouch = 0;
    $('.page-sidebar-minified .sidebar [data-scrollbar=true] a').bind('touchstart', function(e) {
        var touch = e.originalEvent.touches[0] || e.originalEvent.changedTouches[0];
        var touchVertical = touch.pageY;
        oriTouch = touchVertical - parseInt($(this).closest('[data-scrollbar=true]').css('margin-top'));
    });

    $('.page-sidebar-minified .sidebar [data-scrollbar=true] a').bind('touchmove',function(e){
        e.preventDefault();
        if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
            var touch = e.originalEvent.touches[0] || e.originalEvent.changedTouches[0];
            var touchVertical = touch.pageY;
            var elementTop = touchVertical - oriTouch;
            
            $(this).closest('[data-scrollbar=true]').css('margin-top', elementTop + 'px');
        }
    });

    $('.page-sidebar-minified .sidebar [data-scrollbar=true] a').bind('touchend', function(e) {
        var targetScrollBar = $(this).closest('[data-scrollbar=true]');
        var windowHeight = $(window).height();
        var sidebarTopPosition = parseInt($('#sidebar').css('padding-top'));
        var sidebarContainerHeight = $('#sidebar').height();
        oriTouch = $(targetScrollBar).css('margin-top');

        var sidebarHeight = sidebarTopPosition;
        $('.sidebar').not('.sidebar-right').find('.nav').each(function() {
            sidebarHeight += $(this).height();
        });
        var finalHeight = -parseInt(oriTouch) + $('.sidebar').height();
        if (finalHeight >= sidebarHeight && windowHeight <= sidebarHeight && sidebarContainerHeight <= sidebarHeight) {
            var finalMargin = windowHeight - sidebarHeight - 20;
            $(targetScrollBar).animate({marginTop: finalMargin + 'px'});
        } else if (parseInt(oriTouch) >= 0 || sidebarContainerHeight >= sidebarHeight) {
            $(targetScrollBar).animate({marginTop: '0px'});
        } else {
            finalMargin = oriTouch;
            $(targetScrollBar).animate({marginTop: finalMargin + 'px'});
        }
    });
};


/* 07. Handle Clear Sidebar Selection & Hide Mobile Menu - added in V1.9
------------------------------------------------ */
var handleClearSidebarSelection = function() {
    $('.sidebar .nav > li, .sidebar .nav .sub-menu').removeClass('expand').removeAttr('style');
};
var handleClearSidebarMobileSelection = function() {
    $('#page-container').removeClass('page-sidebar-toggled');
};


/* Application Controller
------------------------------------------------ */
var App = function () {
	"use strict";
	
	return {
		//main function
		init: function () {
		    this.initSidebar();
		    this.initComponent();
		},
		initSidebar: function() {
			handleSidebarMenu();
			handleMobileSidebarToggle();
			handleSidebarMinify();
			handleMobileSidebar();
            mydropDown();
            mynavBar();
		},
		initSidebarSelection: function() {
		    handleClearSidebarSelection();
		},
		initSidebarMobileSelection: function() {
		    handleClearSidebarMobileSelection();
		},
		initComponent: function() {
			handleIEFullHeightContent();
			handleSlimScroll();
		},
  };
}();