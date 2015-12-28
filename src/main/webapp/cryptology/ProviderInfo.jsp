<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Provider 信息</title>
    <link rel="stylesheet" href="<c:url value="/resource/style.css" />" />
    <script type="text/javascript" src="<c:url value="/resource/script.js" />"></script>
  </head>
  <script type="text/javascript">
  window.addOnLoadListener(function() {
      var type = $('algorithm');
      type.value = '${algorithm}';
      if(type.value == '') {
        type.value = 'ALL';
      }
    });
  window.addOnLoadListener(function() {
      decorateTable('table');
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
    <h1>Provider 信息</h1>
    <div id="console"></div>
    <form action="<c:url value="/CryptologyServlet" />" method="post">
      <table cellpadding="0">
        <colgroup>
          <col class="width15" />
          <col class="width30" />
          <col class="width15" />
          <col class="width30" />
        </colgroup>
        <tr>
          <th>类型</th>
          <td>
            <select name="algorithm" id="algorithm" class="input">
              <option value="ALL">-- 全部 --</option>
              <option value="AlgorithmParameterGenerator">AlgorithmParameterGenerator</option>
              <option value="AlgorithmParameters">AlgorithmParameters</option>
              <option value="CertPathBuilder">CertPathBuilder</option>
              <option value="CertPathValidator">CertPathValidator</option>
              <option value="CertStore">CertStore</option>
              <option value="CertificateFactory">CertificateFactory</option>
              <option value="Cipher">Cipher</option>
              <option value="Configuration">Configuration</option>
              <option value="GssApiMechanism">GssApiMechanism</option>
              <option value="KeyAgreement">KeyAgreement</option>
              <option value="KeyFactory">KeyFactory</option>
              <option value="KeyGenerator">KeyGenerator</option>
              <option value="KeyInfoFactory">KeyInfoFactory</option>
              <option value="KeyManagerFactory">KeyManagerFactory</option>
              <option value="KeyPairGenerator">KeyPairGenerator</option>
              <option value="KeyStore">KeyStore</option>
              <option value="Mac">Mac</option>
              <option value="MessageDigest">MessageDigest</option>
              <option value="Policy">Policy</option>
              <option value="SSLContext">SSLContext</option>
              <option value="SaslClientFactory">SaslClientFactory</option>
              <option value="SecretKeyFactory">SecretKeyFactory</option>
              <option value="SecureRandom">SecureRandom</option>
              <option value="Signature">Signature</option>
              <option value="TerminalFactory">TerminalFactory</option>
              <option value="TransformService">TransformService</option>
              <option value="TrustManagerFactory">TrustManagerFactory</option>
              <option value="X509Store">X509Store</option>
              <option value="X509StreamParser">X509StreamParser</option>
              <option value="XMLSignatureFactory">XMLSignatureFactory</option>
            </select>
          </td>
          <th>包含 BC</th>
          <td><input type="checkbox" name="bc" value="bc"<c:if test="${bc eq 'bc'}"> checked="checked"</c:if> /></td>
        </tr>
      </table>
      <input type="hidden" name="service" value="ProviderInfo" />
      <input type="hidden" name="data" value="data" />
      <input type="submit" value="查询" />
    </form>
    <span>调用信息: </span>${info}
    <c:if test="${not empty result}">
    <table cellpadding="0" id="table">
      <colgroup>
        <col class="width5" />
        <col class="width10" />
        <col class="width20" />
        <col class="width25" />
        <col class="width40" />
        <col class="width50" />
      </colgroup>
      <thead>
      <tr>
        <th>No.</th>
        <th>名称</th>
        <th>类型</th>
        <th>算法</th>
        <th>实现类名</th>
        <th>其他信息</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${result.algorithms}" var="provider" varStatus="status">
        <tr>
          <td>${status.count}</td>
          <td>${provider.providerName}</td>
          <td>${provider.type}</td>
          <td>${provider.algorithm}</td>
          <td>${provider.className}</td>
          <td>${provider.serviceInfo}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    </c:if>
  </body>
</html>