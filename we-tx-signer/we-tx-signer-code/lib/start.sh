#!/bin/bash

# unzip csp and jcsp distribution

echo $PWD

root_project=$PWD

echo $root_project

apt-get install unzip
tar zxvf $root_project/we-tx-signer/we-tx-signer-code/lib/csp.tgz -C $root_project/we-tx-signer/we-tx-signer-code/lib/
unzip -q $root_project/we-tx-signer/we-tx-signer-code/lib/jcsp.zip -d $root_project/we-tx-signer/we-tx-signer-code/lib/

cd $root_project
# install csp
cd $root_project/we-tx-signer/we-tx-signer-code/lib/linux-amd64_deb
echo "Installing CryptoPro CSP...."
./install.sh

# activate csp
if [ -n "$CSP_LICENSE" ]; then
  echo "Activating CryptoPro CSP..."
  /opt/cprocsp/sbin/amd64/cpconfig -license -set "$CSP_LICENSE"
fi

# print csp license for debug
echo "CryptoPro CSP License"
/opt/cprocsp/sbin/amd64/cpconfig -license -view

# install jcsp
cd $root_project/we-tx-signer/we-tx-signer-code/lib/java-csp-5.0.44121-7e4f5c8b
activation_key=${JCSP_LICENSE:-"PF405-60030-00REK-39KYH-LZXTX"}
company=${COMPANY_NAME:-"we-node"}
./setup_console.sh "$JAVA_HOME"/jre -force -install -jcp -cades -jcsp
echo "$JAVA_HOME"
java ru.CryptoPro.JCSP.JCSPLicense -serial "$activation_key" -company "$company" -store

cd $root_project
# print jcsp license for debug
echo "CryptoPro Java CSP License"
java ru.CryptoPro.JCSP.JCSPLicense

# copy keys from test to root crypto pro keystore
cp -r $root_project/we-tx-signer/we-tx-signer-code/src/test/resources/gost_keystore/* /var/opt/cprocsp/keys/root/

# Add gamma without bio console
# copy gamma source
db_path=/var/opt/cprocsp/dsrf/
mkdir "/var/opt/cprocsp/dsrf/"
cp -r $root_project/we-tx-signer/we-tx-signer-code/lib/gost_gamma/db1/kis_1 $db_path/db1/kis_1
cp -r $root_project/we-tx-signer/we-tx-signer-code/lib/gost_gamma/db2/kis_1 $db_path/db2/kis_1

# setting gamma
/opt/cprocsp/sbin/amd64/cpconfig -hardware rndm -add cpsd -name 'cpsd rng' -level 3
/opt/cprocsp/sbin/amd64/cpconfig -hardware rndm -configure cpsd -add string db1/kis_1 /var/opt/cprocsp/dsrf/db1/kis_1
/opt/cprocsp/sbin/amd64/cpconfig -hardware rndm -configure cpsd -add string db2/kis_1 /var/opt/cprocsp/dsrf/db2/kis_1
chmod +w /private/var/opt/cprocsp/dsrf/db1
chmod +w /private/var/opt/cprocsp/dsrf/db2

# remove redundant files and directories
cd $root_project/we-tx-signer/we-tx-signer-code/lib
rm -r linux-amd64_deb
rm -r java-csp-5.0.44121-7e4f5c8b
rm -f "${JAVA_HOME}"/jre/lib/ext/JCPxml.jar
cd $root_project