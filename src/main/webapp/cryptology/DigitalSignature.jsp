<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>数字签名</title>
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
    <h1>数字签名</h1>
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
              <optgroup label="RSA 数字签名">
              <option value="NONEwithRSA|RSA">NONEwithRSA</option>
              <option value="MD2withRSA|RSA">MD2withRSA</option>
              <option value="MD4withRSA|RSA">MD4withRSA (BC)</option>
              <option value="MD5withRSA|RSA">MD5withRSA</option>
              <option value="SHA1withRSA|RSA">SHA1withRSA</option>
              <option value="SHA224withRSA|RSA">SHA224withRSA (BC)</option>
              <option value="SHA256withRSA|RSA">SHA256withRSA</option>
              <option value="SHA384withRSA|RSA">SHA384withRSA</option>
              <option value="SHA512withRSA|RSA">SHA512withRSA</option>
              <option value="RIPEMD128WithRSA|RSA">RIPEMD128WithRSA (BC)</option>
              <option value="RIPEMD160WithRSA|RSA">RIPEMD160WithRSA (BC)</option>
              <option value="RIPEMD256withRSA|RSA">RIPEMD256withRSA (BC)</option>
              </optgroup>
              <optgroup label="DSA 数字签名">
              <option value="NONEwithDSA|DSA">NONEwithDSA</option>
              <option value="SHA1withDSA|DSA">SHA1withDSA</option>
              <option value="SHA224withDSA|DSA">SHA224withDSA (BC)</option>
              <option value="SHA256withDSA|DSA">SHA256withDSA (BC)</option>
              <option value="SHA384withDSA|DSA">SHA384withDSA (BC)</option>
              <option value="SHA512withDSA|DSA">SHA512withDSA (BC)</option>
              </optgroup>
              <optgroup label="椭圆曲线数字签名">
              <option value="NONEwithECDSA|ECDSA">NONEwithECDSA (BC)</option>
              <option value="SHA1withECDSA|ECDSA">SHA1withECDSA (BC)</option>
              <option value="SHA224withECDSA|ECDSA">SHA224withECDSA (BC)</option>
              <option value="SHA256withECDSA|ECDSA">SHA256withECDSA (BC)</option>
              <option value="SHA384withECDSA|ECDSA">SHA384withECDSA (BC)</option>
              <option value="SHA512withECDSA|ECDSA">SHA512withECDSA (BC)</option>
              </optgroup>
            </select>
          </td>
          <th>密钥长度</th>
          <td><input type="text" name="keysize" value="${keysize}" class="input" /></td>
        </tr>
        <tr>
          <th>密钥HEX</th>
          <td colspan="3"><textarea name="key" class="input">${key}</textarea></td>
        </tr>
        <tr>
          <th>签名HEX</th>
          <td colspan="3"><textarea name="signature" class="input">${signature}</textarea></td>
        </tr>
        <tr>
          <th>签名验证</th>
          <td>
            <input type="radio" name="mode" value="1" checked="checked" />私钥签名
            <input type="radio" name="mode" value="2" <c:if test="${mode eq '2'}"> checked="checked"</c:if> />公钥验证
          </td>
          <th>使用 BC</th>
          <td><input type="checkbox" name="bc" value="bc"<c:if test="${bc eq 'bc'}"> checked="checked"</c:if> /></td>
        </tr>
      </table>
      <input type="hidden" name="service" value="DigitalSignature" />
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
        <th>签名算法</th>
        <td>${result.algorithm}</td>
        <th>Provider</th>
        <td>${result.provider}</td>
      </tr>
      <c:if test="${mode eq 1}">
        <tr>
          <th>签名长度</th>
          <td colspan="3">${result.crypto.bitLength} bit(s), ${result.crypto.length} byte(s)</td>
        </tr>
        <tr>
          <th>签名数据<br/>(HEX)</th>
          <td colspan="3"><textarea readonly="readonly">${result.crypto.hex}</textarea></td>
        </tr>
        <tr>
          <th>签名数据<br/>(Base64)</th>
          <td colspan="3"><textarea readonly="readonly">${result.crypto.base64}</textarea></td>
        </tr>
      </c:if>
      <c:if test="${mode eq 2}">
        <tr>
          <th>验证结果</th>
          <td colspan="3">${result.verifySignature}</td>
        </tr>
      </c:if>

      <c:if test="${not empty result.publicKey}">
      <tr>
        <th>公钥长度</th>
        <td colspan="3">${result.publicKey.bitLength} bit(s), ${result.publicKey.length} byte(s)</td>
      </tr>
      <tr>
        <th>公钥<br/>(HEX)</th>
        <td colspan="3"><textarea readonly="readonly">${result.publicKey.hex}</textarea></td>
      </tr>
      </c:if>

      <c:if test="${not empty result.privateKey}">
      <tr>
        <th>私钥长度</th>
        <td colspan="3">${result.privateKey.bitLength} bit(s), ${result.privateKey.length} byte(s)</td>
      </tr>
      <tr>
        <th>私钥<br/>(HEX)</th>
        <td colspan="3"><textarea readonly="readonly">${result.privateKey.hex}</textarea></td>
      </tr>
      </c:if>
    </table>
    </c:if>
  </body>
</html>