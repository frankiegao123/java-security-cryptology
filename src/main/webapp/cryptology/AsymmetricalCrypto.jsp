<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>非对称加密</title>
    <link rel="stylesheet" href="<c:url value="/resource/style.css" />" />
    <script type="text/javascript" src="<c:url value="/resource/script.js" />"></script>
  </head>
  <body>
    <h1>非对称加密</h1>
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
          <td><input type="text" name="algorithm" value="${algorithm}" class="input" /></td>
          <th>密钥长度</th>
          <td><input type="text" name="keysize" value="${keysize}" class="input" /></td>
        </tr>
        <tr>
          <th>密钥HEX</th>
          <td colspan="3"><textarea name="key" class="input">${key}</textarea></td>
        </tr>
        <tr>
          <th rowspan="2">加密解密</th>
          <td>
            <input type="radio" name="mode" value="1" checked="checked" />公钥加密
            <input type="radio" name="mode" value="4" <c:if test="${mode eq '4'}"> checked="checked"</c:if> />私钥解密           
          </td>
          <th rowspan="2">使用 BC</th>
          <td rowspan="2"><input type="checkbox" name="bc" value="bc"<c:if test="${bc eq 'bc'}"> checked="checked"</c:if> /></td>
        </tr>
        <tr>
          <td><input type="radio" name="mode" value="3" <c:if test="${mode eq '3'}"> checked="checked"</c:if> />私钥加密
          <input type="radio" name="mode" value="2" <c:if test="${mode eq '2'}"> checked="checked"</c:if> />公钥解密</td>
        </tr>
      </table>
      <input type="hidden" name="service" value="AsymmetricalCrypto" />
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
        <th>加密算法</th>
        <td>${result.algorithm}</td>
        <th>Provider</th>
        <td>${result.provider}</td>
      </tr>
      <c:if test="${(mode eq 1) or (mode eq 3)}">
        <tr>
          <th>密文长度</th>
          <td colspan="3">${result.crypto.bitLength} bit(s), ${result.crypto.length} byte(s)</td>
        </tr>
        <tr>
          <th>密文<br/>(HEX)</th>
          <td colspan="3"><textarea readonly="readonly">${result.crypto.hex}</textarea></td>
        </tr>
        <tr>
          <th>密文<br/>(Base64)</th>
          <td colspan="3"><textarea readonly="readonly">${result.crypto.base64}</textarea></td>
        </tr>
      </c:if>
      <c:if test="${(mode eq 2) or (mode eq 4)}">
        <tr>
          <th>解密明文</th>
          <td colspan="3"><c:out value="${result.crypto.string}" /></td>
        </tr>
        <tr>
          <th>解密明文<br/>(HEX)</th>
          <td colspan="3"><textarea readonly="readonly">${result.crypto.hex}</textarea></td>
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