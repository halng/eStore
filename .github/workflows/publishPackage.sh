#!/bin/bash

set -e  # Exit on any error
cd ui-api

# Get the package name and version from package.json
package_name=$(jq -r .name package.json)
package_version=$(jq -r .version package.json)

# Check if the version exists on the registry
npm show $package_name@$package_version versions > /dev/null 2>&1
if [[ $? -eq 0 ]]; then
 # Version exists, comment on PR
 gh pr comment --body "The version $package_version exists, Skip publish"
else
 # Version doesn't exist, publish and comment on PR
 npm publish
 gh pr comment --body "Publish new version $package_version success"
fi
