/** @type {import('next').NextConfig} */
const nextConfig = {
  modularizeImports: {
    "react-bootstrap": {
      transform: "react-bootstrap/{{member}}",
    },
    lodash: {
      transform: "lodash/{{member}}",
    },
  },
  reactStrictMode: true,
  output: "standalone",
};

module.exports = nextConfig;
