const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = function (app) {
  const options = {
    target: "http://rezerwacje-app:8080/",
    pathRewrite: {
      "^/api/": "/", // remove base path
    },
    changeOrigin: true,
  };
  app.use(createProxyMiddleware("/api", options));
};
