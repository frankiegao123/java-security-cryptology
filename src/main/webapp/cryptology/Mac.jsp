<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>消息认证码</title>
    <link rel="stylesheet" href="<c:url value="/resource/style.css" />" />
    <script type="text/javascript" src="<c:url value="/resource/script.js" />"></script>
  </head>
  <script type="text/javascript">
  window.addOnLoadListener(function() {
	    var alg = $('algorithm');
      alg.value = '${algorithm}';
      if(alg.value == '') {
        alg.value = alg.options[0].value;
      }
    });
  window.addOnLoadListener(function() {
      var alg = $('algorithm');
      var opts = alg.options;
      for(var i = 0, k = opts.length; i < k; i++) {
        opts[i].title = opts[i].innerHTML;
      }
    });
  </script>
  <body>
    <h1>消息认证码</h1>
    <form action="<c:url value="/CryptologyServlet" />" method="post">
      <table cellpadding="0">
        <colgroup>
          <col class="width15" />
          <col class="width30" />
          <col class="width15" />
          <col class="width30" />
        </colgroup>
        <tr>
          <th>数据</th>
          <td colspan="3"><textarea name="data" class="input">${data}</textarea></td>
        </tr>
        <tr>
          <th>算法</th>
          <td>
            <select name="algorithm" id="algorithm" class="input">
              <option value="HmacMD2">HmacMD2 (BC)</option>
              <option value="HmacMD4">HmacMD4 (BC)</option>
              <option value="HmacMD5">HmacMD5</option>
              <option value="HmacSHA1">HmacSHA1</option>
              <option value="HmacSHA224">HmacSHA224 (BC)</option>
              <option value="HmacSHA256">HmacSHA256</option>
              <option value="HmacSHA384">HmacSHA384</option>
              <option value="HmacSHA512">HmacSHA512</option>              
              <option value="HmacRipeMD128">HmacRipeMD128 (BC)</option>
              <option value="HmacRipeMD160">HmacRipeMD160 (BC)</option>
              <option value="HmacTiger">HmacTiger (BC)</option>
              <option value="GOST28147">GOST28147 (BC)</option>
            </select>
          </td>
          <th>使用 BC</th>
          <td><input type="checkbox" name="bc" value="bc"<c:if test="${bc eq 'bc'}"> checked="checked"</c:if> /></td>
        </tr>
        <tr>
          <th>密钥HEX</th>
          <td colspan="3"><textarea name="key" class="input">${key}</textarea></td>
        </tr>
      </table>
      <input type="hidden" name="service" value="Mac" />
      <input type="submit" value="提交" />
    </form>
    <span>调用信息: </span>${info}
    <c:if test="${not empty result}">
    <table cellpadding="0">
      <colgroup>
        <col class="width15" />
        <col class="width30" />
        <col class="width15" />
        <col class="width30" />
      </colgroup>
      <tr>
        <th>MAC算法</th>
        <td>${result.algorithm}</td>
        <th>Provider</th>
        <td>${result.provider}</td>
      </tr>
      <tr>
        <th>摘要长度</th>
        <td colspan="3">${result.crypto.bitLength} bit(s), ${result.crypto.length} byte(s)</td>
      </tr>
      <tr>
        <th>摘要<br/>(HEX)</th>
        <td colspan="3"><textarea readonly="readonly">${result.crypto.hex}</textarea></td>
      </tr>
      <tr>
        <th>摘要<br/>(Base64)</th>
        <td colspan="3"><textarea readonly="readonly">${result.crypto.base64}</textarea></td>
      </tr>
      <tr>
        <th>密钥长度</th>
        <td colspan="3">${result.key.bitLength} bit(s), ${result.key.length} byte(s)</td>
      </tr>
      <tr>
        <th>密钥<br/>(HEX)</th>
        <td colspan="3"><textarea readonly="readonly">${result.key.hex}</textarea></td>
      </tr>
    </table>
    </c:if>
  </body>
</html>