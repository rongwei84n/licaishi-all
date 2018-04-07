npm run build
tar -zcvf lcs-cli.tar.gz dist/

scp lcs-cli.tar.gz root@47.97.100.240:/root/deploy/lcs-cli/front
