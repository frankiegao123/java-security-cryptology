<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
* {font-size:20px;line-height:1.4;}
a,a:active,a:visited {text-decoration:none;color:blue;}
a:hover {color:red;text-decoration:none;position:relative;top:1px;left:1px;}
a.select {font-weight:bold;color:red;}
</style>

<script type="text/javascript">
window.onload = function() {
  var as = document.getElementsByTagName('a');
  if(!as) return;

  for(var i = 0; i < as.length; i++) {
    as[i].onclick = function() {
        reset(as);
        this.className = 'select';
      };
  }
}

function reset(as) {
  for(var i = 0; i < as.length; i++) {
    as[i].className = '';
  }
}
</script>
<html>
<body>
  <a href="<c:url value="/cryptology/ProviderInfo.jsp" />" target="content">Provider 信息</a><br />
  <a href="<c:url value="/cryptology/MessageDigest.jsp" />" target="content">消息摘要</a><br />
  <a href="<c:url value="/cryptology/Mac.jsp" />" target="content">消息认证码(MAC)</a><br />
  <a href="<c:url value="/cryptology/SymmetricalCrypto.jsp" />" target="content">对称加密</a><br />
  <a href="<c:url value="/cryptology/PBECrypto.jsp" />" target="content">口令对称加密</a><br />
  <a href="<c:url value="/cryptology/AsymmetricalCrypto.jsp" />" target="content">非对称加密</a><br />
  <a href="<c:url value="/cryptology/DigitalSignature.jsp" />" target="content">数字签名</a><br />
</body>
</html>