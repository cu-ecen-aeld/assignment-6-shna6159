# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/cu-ecen-aeld/assignments-3-and-later-shna6159.git;protocol=https;branch=main \
           file://S99aesdchar \
           "

PV = "1.0+git${SRCPV}"
SRCREV = "d9f97f7b07750b9b9a3df0c33dd04d5b976ce070"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
RPROVIDES:${PN} += "kernel-module-aesdchar"


INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S99aesdchar"
inherit update-rc.d

FILES:${PN} += "${INIT_D_DIR}/${INITSCRIPT_NAME:${PN}}"

FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar_load"
FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar_unload"


#inherit logging

do_install:append () {
        # Install your binaries/scripts here.
        # Be sure to install the target directory with install -d first
        # Yocto variables ${D} and ${S} are useful here, which you can read about at 
        # https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
        # and
        # https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
        # See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb

        install -d ${D}${INIT_D_DIR}
        install -m 0755 ${WORKDIR}/${INITSCRIPT_NAME:${PN}} ${D}${INIT_D_DIR}
        #bbwarn "install -m 0755 ${WORKDIR}/${INITSCRIPT_NAME:${PN}} ${D}${INIT_D_DIR}"

	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
	install -m 755 ${S}/aesdchar_load ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar_load
	install -m 755 ${S}/aesdchar_unload ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar_unload
}
