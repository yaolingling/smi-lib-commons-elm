/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.commons.elm.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.dell.isg.smi.commons.elm.exception.InvalidArgumentsException;
import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.commons.elm.model.EnumErrorCode;

public class FileUtils {

    public static final Pattern CIFS_PATTERN = Pattern.compile("[\\\\][\\\\][^\\\\].{0,255}[\\\\]{1}[^\\\\]*.*");

    public static final Pattern NFS_FILE_PATTERN = Pattern.compile(".*[:]{1}[/]{1}[^/].*[.]{1}.*$");

    public static final Pattern CIFS_FILE_PATTERN = Pattern.compile("[\\\\][\\\\][^\\\\].{0,255}[\\\\]{1}[^\\\\]*.*[.]{1}.*");


    public static boolean isValidFilePath(String path, String domain, String shareUsername, String sharePassword) throws InvalidArgumentsException {

        if (StringUtils.isNotEmpty(path) && StringUtils.isNotBlank(path)) {
            if (isNFSFilePath(path)) {
                return true;
            } else if (isCIFSFilePath(path)) {
                // validate if the username and password are provided or not.
                return validateCifsUserinfo(domain, shareUsername, sharePassword);
            }
        } else {
            RuntimeCoreException.handleRuntimeCoreException(EnumErrorCode.ENUM_INVALID_DATA, "Share Path");
        }

        return false;
    }


    public static boolean isCIFSFilePath(String cifsFilePath) {
        if (StringUtils.isNotEmpty(cifsFilePath) && StringUtils.isNotBlank(cifsFilePath)) {
            if (cifsFilePath.endsWith("\\")) {
                cifsFilePath = cifsFilePath.substring(0, cifsFilePath.length() - 1);
            }
            Matcher nfsPattern = CIFS_FILE_PATTERN.matcher(cifsFilePath);
            return nfsPattern.matches();
        }

        return false;
    }


    public static boolean isNFSFilePath(String shareFilePath) {
        if (shareFilePath != null) {
            return NFS_FILE_PATTERN.matcher(shareFilePath).matches();
        }

        return false;
    }


    public static boolean validateCifsUserinfo(String checkDomain, String checkUser, String checkPassword) {
        // final String AT = "@";
        // final String PERCENT = "%";
        boolean result = true;

        // if (checkUser == null || checkUser.isEmpty() || checkUser.contains(AT) || checkDomain.contains(PERCENT)) {
        // ExceptionUtilities.handleRuntimeSpectre(2008);
        // }
        // if (checkPassword == null || checkPassword.isEmpty() || checkPassword.contains(AT) ||
        // checkPassword.contains(PERCENT) ) {
        // ExceptionUtilities.handleRuntimeSpectre(2007);
        // }
        //
        // if ( checkDomain != null && ( checkDomain.contains(AT) || checkDomain.contains(PERCENT) ) )
        // {
        // ExceptionUtilities.handleRuntimeSpectre(2009);
        // }

        return result;
    }

}
