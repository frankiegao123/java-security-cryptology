<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>消息摘要</title>
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
    <h1>消息摘要</h1>
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
              <option value="MD2">MD2</option>
              <option value="MD4">MD4 (BC)</option>
              <option value="MD5">MD5</option>
              <option value="SHA-1">SHA-1</option>
              <option value="SHA-224">SHA-224 (BC)</option>
              <option value="SHA-256">SHA-256</option>
              <option value="SHA-384">SHA-384</option>
              <option value="SHA-512">SHA-512</option>              
              <option value="RipeMD128">RipeMD128 (BC)</option>
              <option value="RipeMD160">RipeMD160 (BC)</option>
              <option value="RipeMD256">RipeMD256 (BC)</option>
              <option value="RipeMD320">RipeMD320 (BC)</option>
              <option value="GOST3411">GOST3411 (BC)</option>
              <option value="Tiger">Tiger (BC)</option>
              <option value="WHIRLPOOL">WHIRLPOOL (BC)</option>      
            </select>
          </td>
          <th>使用 BC</th>
          <td><input type="checkbox" name="bc" value="bc"<c:if test="${bc eq 'bc'}"> checked="checked"</c:if> /></td>
        </tr>
      </table>
      <input type="hidden" name="service" value="MessageDigest" />
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
        <th>摘要算法</th>
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
    </table>
    </c:if>
  </body>
</html>