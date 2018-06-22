ace.define("ace/theme/pbcore", ["require", "exports", "module", "ace/lib/dom"], function (require, exports, module) {
    "use strict";

    exports.isDark = false;
    exports.cssClass = "ace-pbcore";
    exports.cssText = ".ace-pbcore .ace_gutter {\
background: #ebebeb;\
border-right: 1px solid rgb(159, 159, 159);\
color: rgb(136, 136, 136);\
}\
.ace-pbcore .ace_print-margin {\
width: 1px;\
background: #ebebeb;\
}\
.ace-pbcore {\
background-color: #FFFFFF;\
color: #1D1D1D;\
}\
.ace-pbcore .ace_fold {\
background-color: rgb(60, 76, 114);\
}\
.ace-pbcore .ace_cursor {\
color: black;\
}\
.ace-pbcore .ace_storage,\
.ace-pbcore .ace_keyword,\
.ace-pbcore .ace_variable {\
color: rgb(127, 0, 85);\
}\
.ace-pbcore .ace_constant.ace_buildin {\
color: rgb(88, 72, 246);\
}\
.ace-pbcore .ace_constant.ace_library {\
color: rgb(6, 150, 14);\
}\
.ace-pbcore .ace_function {\
color: rgb(60, 76, 114);\
}\
.ace-pbcore .ace_string {\
color: #434343;\
}\
.ace-pbcore .ace_comment {\
color: rgb(113, 150, 130);\
}\
.ace-pbcore .ace_comment.ace_doc {\
color: rgb(63, 95, 191);\
}\
.ace-pbcore .ace_comment.ace_doc.ace_tag {\
color: rgb(127, 159, 191);\
}\
.ace-pbcore .ace_constant.ace_numeric {\
color: darkblue;\
}\
.ace-pbcore .ace_tag {\
color: rgb(25, 118, 116);\
}\
.ace-pbcore .ace_type {\
color: rgb(127, 0, 127);\
}\
.ace-pbcore .ace_xml-pe {\
color: rgb(104, 104, 91);\
}\
.ace-pbcore .ace_marker-layer .ace_selection {\
background: rgb(181, 213, 255);\
}\
.ace-pbcore .ace_marker-layer .ace_bracket {\
margin: -1px 0 0 -1px;\
border: 1px solid rgb(192, 192, 192);\
}\
.ace-pbcore .ace_meta.ace_tag {\
color:#0B74D3;\
}\
.ace-pbcore .ace_invisible {\
color: #ddd;\
}\
.ace-pbcore .ace_entity.ace_other.ace_attribute-name {\
color:#11C115;\
}\
.ace-pbcore .ace_marker-layer .ace_step {\
background: rgb(255, 255, 0);\
}\
.ace-pbcore .ace_active-line {\
background: rgb(232, 242, 254);\
}\
.ace-pbcore .ace_gutter-active-line {\
background-color : #DADADA;\
}\
.ace-pbcore .ace_marker-layer .ace_selected-word {\
border: 1px solid rgb(181, 213, 255);\
}\
.ace-pbcore .ace_indent-guide {\
background: url(\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAACCAYAAACZgbYnAAAAE0lEQVQImWP4////f4bLly//BwAmVgd1/w11/gAAAABJRU5ErkJggg==\") right repeat-y;\
}";

    exports.cssClass = "ace-pbcore";

    var dom = require("../lib/dom");
    dom.importCssString(exports.cssText, exports.cssClass);
});
