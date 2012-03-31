Name: NIARL_OAT_Standalone
Version: 2.0
Release: 1
Summary: OAT project
License: Proprietary
Group: System Environment/Base
Source: %{name}.tar.gz
BuildRoot: %{_tmppath}/%{name}-root
BuildRequires: java
BuildArch: x86_64

%description
The NIARL_OAT_Standalone is a program by the National Information Assurance
Research Laboratory (NIARL) that uses Java and the NIARL_TPM_Module to
acquire integrity measurement data from a host's Trusted Platform Module
(TPM). The data is compiled
into an integrity report and sent to the OAT appraisal server. This package
does not automatically add the OAT Standalone startup script.

%prep
%setup -q -n %{name}

%build

%install
mkdir -p %{buildroot}/etc/init.d
chmod +x OAT.sh
cp -f OAT.sh %{buildroot}/etc/init.d/OAT.sh
mkdir -p %{buildroot}/OAT
cp -rf * %{buildroot}/OAT

%post
ln -fs /etc/init.d/OAT.sh /etc/rc5.d/S99OAT
rm -f /OAT/uninstallOAT.sh
echo "/etc/init.d/OAT.sh stop" >> /OAT/uninstallOAT.sh
echo "rpm -e "%{name}"-"%{version}"-"%{release} >> /OAT/uninstallOAT.sh
echo "rm -rf /OAT" >> /OAT/uninstallOAT.sh
echo "rm -f /etc/rc5.d/S99OAT" >> /OAT/uninstallOAT.sh

%clean
rm -rf %{buildroot}/OAT/lib
rm -f %{buildroot}/OAT/OAT.properties
rm -f %{buildroot}/OAT/OAT07.jpg
rm -f %{buildroot}/OAT/OAT_Standalone.jar
rm -f %{buildroot}/OAT/etc/rc5.d/S99OAT
rm -f %{buildroot}/OAT/etc/init.d/OAT.sh

%files
/OAT/OAT.sh
/OAT/OAT07.jpg
/OAT/OAT_Standalone.jar
/OAT/log4j.properties
#/OAT/OAT.properties
/OAT/lib/FastInfoset.jar
/OAT/lib/HisWebServices-client.jar
#/OAT/lib/TokenUtil.jar
/OAT/lib/activation.jar
#/OAT/lib/am_sdk.jar
#/OAT/lib/amclientsdk.jar
#/OAT/lib/bbxasc.jar
#/OAT/lib/bbxcore.jar
#/OAT/lib/bbxdatamodel.jar
#/OAT/lib/bbxutil.jar
/OAT/lib/commons-beanutils.jar
/OAT/lib/commons-collections.jar
/OAT/lib/commons-digester.jar
/OAT/lib/commons-logging.jar
#/OAT/lib/http.jar
/OAT/lib/jaas.jar
/OAT/lib/jax-qname.jar
/OAT/lib/jaxb-api.jar
/OAT/lib/jaxb-impl.jar
/OAT/lib/jaxb-libs.jar
/OAT/lib/jaxb-xjc.jar
/OAT/lib/jaxws-api.jar
/OAT/lib/jaxws-rt.jar
/OAT/lib/jaxws-tools.jar
/OAT/lib/jsr173_api.jar
/OAT/lib/jsr181-api.jar
/OAT/lib/jsr250-api.jar
/OAT/lib/jta-spec1_0_1.jar
/OAT/lib/log4j-1.2.14.jar
/OAT/lib/mail.jar
/OAT/lib/mimepull.jar
#/OAT/lib/namespace.jar
/OAT/lib/relaxngDatatype.jar
/OAT/lib/resolver.jar
/OAT/lib/saaj-api.jar
/OAT/lib/saaj-impl.jar
/OAT/lib/servlet-api.jar
/OAT/lib/stax-ex.jar
/OAT/lib/streambuffer.jar
/OAT/lib/uuid-3.2.jar
/OAT/lib/woodstox.jar
/OAT/lib/xsdlib.jar
/etc/init.d/OAT.sh
