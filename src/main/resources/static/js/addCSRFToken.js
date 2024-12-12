function addCSRFToken() {
  const token = $("meta[name='_csrf']").attr("content");
  // console.log("Token CSRF: ", token);
  const header = $("meta[name='_csrf_header']").attr("content");
  // console.log("Header CSRF: ", header);

  $(document).ajaxSend((e, xhr, options) => {
    xhr.setRequestHeader(header, token);
  });

  console.log("CSRF token completed...");
}
