# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
# 
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-shna6159.git;protocol=ssh;branch=main \
			file://aesd-char-driver_init \
			"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "5937c8c7367e3093103ff7beb2cd4f5cc18eda3f"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesd-char-driver_init"


KERNEL_VERSION = "5.15.124-yocto-standard"

FILES:${PN} += "${sysconfdir}/*"
FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/aesdchar_load"
FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/aesdchar_unload"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}


do_install () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/aesd-char-driver_init ${D}${sysconfdir}/init.d
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
	install -m 0755 ${S}/aesdchar_unload ${D}${base_libdir}/modules/${KERNEL_VERSION}/
	install -m 0755 ${S}/aesdchar_load ${D}${base_libdir}/modules/${KERNEL_VERSION}/
	install -m 0755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
}
