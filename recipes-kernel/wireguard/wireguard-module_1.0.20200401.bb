SUMMARY = "WireGuard is an extremely simple yet fast and modern VPN"
DESCRIPTION="WireGuard is a secure network tunnel, operating at layer 3, \
implemented as a kernel virtual network interface for Linux, which aims to \
replace both IPsec for most use cases, as well as popular user space and/or \
TLS-based solutions like OpenVPN, while being more secure, more performant, \
and easier to use."
SECTION = "networking"
HOMEPAGE = "https://www.wireguard.io/"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://../COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "https://git.zx2c4.com/wireguard-linux-compat/snapshot/wireguard-linux-compat-${PV}.tar.xz"

SRC_URI[md5sum] = "f553dd985e748f7df37ff2da942adac5"
SRC_URI[sha256sum] = "7dfb4a8315e1d6ae406ff32d01c496175df558dd65968a19e5222d02c7cfb77a"

inherit module kernel-module-split

DEPENDS = "virtual/kernel libmnl"

EXTRA_OEMAKE_append = " \
    KERNELDIR=${STAGING_KERNEL_DIR} \
"

MAKE_TARGETS = "module"

RRECOMMENDS_${PN} = "kernel-module-xt-hashlimit"
MODULE_NAME = "wireguard"

S = "${WORKDIR}/wireguard-linux-compat-${PV}/src"


# Kernel module packages MUST begin with 'kernel-module-', otherwise
# multilib image generation can fail.
#
# The following line is only necessary if the recipe name does not begin
# with kernel-module-.
PKG_${PN} = "kernel-module-${MODULE_NAME}"

module_do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/${MODULE_NAME}
    install -m 0644 ${MODULE_NAME}.ko \
    ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/${MODULE_NAME}/${MODULE_NAME}.ko
}
