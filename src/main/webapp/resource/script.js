window.$ = function(id) {
  return (typeof id == 'string') ? document.getElementById(id) : id;
}

var DEFAULT_BORDER_COLOR = '#c0c0c0';
var FOCUS_BORDER_COLOR = '#0044bb';
var DEFAULT_BACKGROUND_COLOR = 'white';
var FOCUS_BACKGROUND_COLOR = '#ffee99';

function remove(obj) {
  var clickedRow = obj;
  while(clickedRow.tagName != "TR") {
    clickedRow = clickedRow.parentNode;
  }
  clickedRow.parentNode.removeChild(clickedRow);
  var rows = document.getElementsByName('row');
  if(rows) {
    for(var i = 0; i < rows.length; i++) {
      rows[i].value = i + 1;
    }
  }
  var randoms = document.getElementsByName('random');
  if(randoms) {
        for(var i = 0; i < rows.length; i++) {
      randoms[i].value = i + 1;
    }
  }
}

window.onloadListeners = [];

window.addOnLoadListener = function(listener) {
  window.onloadListeners.push(listener);
}

window.onload = function() {
  addEvent(document.getElementsByTagName('input'));
  addEvent(document.getElementsByTagName('textarea'));
  addEvent(document.getElementsByTagName('select'));
  for(var i = 0; i < window.onloadListeners.length; i++) {
    window.onloadListeners[i].call();
  }
}

function addEvent(inps) {
  for(var i = 0; i < inps.length; i++) {
	if(inps[i].className != 'input' && inps[i].tagName != 'textarea' && inps[i].tagName != 'select') {
	  continue;
	}
	inps[i].ft = false;
	inps[i].onmouseover = function() {
	    this.style.borderColor = FOCUS_BORDER_COLOR;
	    this.style.backgroundColor = FOCUS_BACKGROUND_COLOR;
	  }
	inps[i].onmouseout = function() {
	    if(!this.ft) {
	      this.style.borderColor = '#c0c0c0';
	      this.style.backgroundColor = DEFAULT_BACKGROUND_COLOR;
	    }
	  }
	inps[i].onfocus = function() {
	    this.style.borderColor = FOCUS_BORDER_COLOR;
	    this.style.backgroundColor = FOCUS_BACKGROUND_COLOR;
	    this.ft = true;
	  }
	inps[i].onblur = function() {
	    this.style.borderColor = DEFAULT_BORDER_COLOR;
	    this.style.backgroundColor = DEFAULT_BACKGROUND_COLOR;
	    this.ft = false;
      }
	if ( inps[i].name == 'data' ) {
	  inps[i].placeholder = '字节输入时，以十六进制外加方括号';
	}
	if ( inps[i].name == 'key' ) {
	  inps[i].placeholder = '密钥为空时，随机生成';
	}
	if ( inps[i].name == 'parameterSpec' ) {
	  inps[i].placeholder = '为空时，随机生成';
	}
  }
}

function decorateTable(id) {
  var table = $(id);
  if(!table) {
    return;
  }
  var rows = table.rows;
  for(var i = 1, k = rows.length; i < k; i++) {
    if(i % 2 == 0) {
      rows[i].className = 'even';
    }
    rows[i].onmouseover = function() {
        this.className += ' over';
      };
    rows[i].onmouseout = function() {
        this.className = this.className.replace(/ over/, '');
      };
  }
}