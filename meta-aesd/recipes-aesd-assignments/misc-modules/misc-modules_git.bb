# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-shna6159.git;protocol=https;branch=main \
           file://S98misc-modules \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "1cc995ca86e9ff59212756f16483528dd77438fc"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_install () {
	# Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb
	
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
	install -m 755 ${S}/misc-modules/hello.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/hello.ko
	install -m 755 ${S}/misc-modules/faulty.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/faulty.ko
	
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/S98misc-modules ${D}${sysconfdir}/init.d
}

FILES_${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/extra/hello.ko"
FILES_${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/extra/faulty.ko"

FILES:${PN} += "${sysconfdir}/init.d/S98misc-modules"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S98misc-modules"



