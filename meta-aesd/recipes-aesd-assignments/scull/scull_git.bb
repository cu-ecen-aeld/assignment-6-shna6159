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

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-d4d0o.git;protocol=https;branch=main \
           file://0001-limit-modules-build-to-scull-and-misc-modules.patch \
           file://S97scullmodule \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "48f26b9c92bddfd4c87e1f2863c5114eb459e068"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
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
	install -m 755 ${S}/scull/scull.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/scull.ko
	
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/S97scullmodule ${D}${sysconfdir}/init.d
}

FILES_${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/extra/scull.ko"

FILES:${PN} += "${sysconfdir}/init.d/S97scullmodule"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S97scullmodule"



