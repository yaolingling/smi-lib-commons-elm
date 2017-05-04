/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
/**
 *
 */
package com.dell.isg.smi.commons.elm.messaging;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import static com.dell.isg.smi.commons.elm.CommonConstants.DEFAULT_CLIENT_LOCALE;

import com.dell.isg.smi.commons.elm.bundle.ResourceBundleReader;
import com.dell.isg.smi.commons.elm.messaging.IMessageReader;

/**
 *
 */

public class FriendlyMessageBundleReader implements IMessageReader {
    private Object _tag = null;

    @SuppressWarnings("all")
    private class OrderedProperties extends Properties {

        @SuppressWarnings("rawtypes")
        private Vector _names;


        public OrderedProperties() {
            super();
            _names = new Vector();
        }


        @Override
        public Enumeration propertyNames() {
            return _names.elements();
        }


        @Override
        public Object put(Object key, Object value) {
            if (_names.contains(key)) {
                _names.remove(key);
            }
            _names.add(key);
            return super.put(key, value);
        }


        @Override
        public Object remove(Object key) {
            _names.remove(key);
            return super.remove(key);
        }
    }

    private static final Logger log = LoggerFactory.getLogger(FriendlyMessageBundleReader.class);

    private OrderedProperties bundles;

    private Locale locale;


    public FriendlyMessageBundleReader() {
        this.locale = LocaleContextHolder.getLocale();
    }


    public FriendlyMessageBundleReader(Locale locale2) {
        this.locale = locale2;
    }


    private void loadBundleNames() throws IOException {
        InputStream stream = null;
        try {
            bundles = new OrderedProperties();
            stream = this.getClass().getClassLoader().getResourceAsStream("AvailableBundle.properties");
            bundles.load(stream);
        } catch (Exception e) {
            log.error("Error in loadBundleNames()");
        } finally {
            if (stream != null) {
                stream.close();
                stream = null;
            }
        }
    }


    /**
     *
     */
    @Override
    public String getMessage(String msgCode) {
        try {
            boolean loadUSDefault = true;
            loadBundleNames();

            while (loadUSDefault) {
                for (@SuppressWarnings("rawtypes")
                Enumeration bundleKeys = bundles.propertyNames(); bundleKeys.hasMoreElements();) {
                    String key = (String) bundleKeys.nextElement();
                    String value = bundles.getProperty(key);
                    try {
                        ResourceBundleReader reader = new ResourceBundleReader(this.locale, value);
                        if (reader != null && reader.getString(msgCode) != null) {
                            return reader.getString(msgCode);
                        }
                    } catch (Exception e) {
                        // Doesn't matter. Eat it. Search in the next bundle
                    }
                }

                log.debug("Could not find the bundle or resource string for message code: " + msgCode);
                if (!this.locale.equals(StringUtils.parseLocaleString(DEFAULT_CLIENT_LOCALE))) {
                    this.locale = StringUtils.parseLocaleString(DEFAULT_CLIENT_LOCALE);
                } else {
                    loadUSDefault = false;
                }
            }
        } catch (Exception e) {
            log.debug("Could not find available resource bundle names for Locale: " + this.locale.getDisplayName() + " and message code: " + msgCode);
        }

        // If cannot translate msgCode then just return msgCode.
        return msgCode;
    }


    /**
     *
     */
    @Override
    public String getMessage(String msgCode, Object[] attributes) {
        try {
            boolean loadUSDefault = true;
            loadBundleNames();

            while (loadUSDefault) {
                for (@SuppressWarnings("rawtypes")
                Enumeration bundleKeys = bundles.propertyNames(); bundleKeys.hasMoreElements();) {
                    String key = (String) bundleKeys.nextElement();
                    String value = bundles.getProperty(key);
                    try {
                        ResourceBundleReader reader = new ResourceBundleReader(locale, value);
                        if (reader != null && reader.getString(msgCode, attributes) != null) {
                            return reader.getString(msgCode, attributes);
                        }
                    } catch (Exception e) {
                        // Doesn't matter. Eat it. Search in the next bundle
                    }
                }

                log.debug("Could not find the bundle or resource string for message code: " + msgCode);
                if (!this.locale.equals(StringUtils.parseLocaleString(DEFAULT_CLIENT_LOCALE))) {
                    this.locale = StringUtils.parseLocaleString(DEFAULT_CLIENT_LOCALE);
                } else {
                    loadUSDefault = false;
                }
            }
        } catch (Exception e) {
            log.debug("Could not find available resource bundle names for Locale: " + this.locale.getDisplayName() + " and message code: " + msgCode);
        }

        // If cannot translate msgCode then just return msgCode
        return msgCode;
    }


    /**
     *
     */
    public String getMessage(String msgCode, String bundleName) {
        try {
            boolean loadUSDefault = true;
            loadBundleNames();

            while (loadUSDefault) {
                for (@SuppressWarnings("rawtypes")
                Enumeration bundleKeys = bundles.propertyNames(); bundleKeys.hasMoreElements();) {
                    String key = (String) bundleKeys.nextElement();

                    if (key.equalsIgnoreCase(bundleName)) {
                        String value = bundles.getProperty(key);
                        try {
                            ResourceBundleReader reader = new ResourceBundleReader(locale, value);
                            if (reader != null && reader.getString(msgCode) != null) {
                                return reader.getString(msgCode);
                            }
                        } catch (Exception e) {
                            // Doesn't matter. Eat it. Search in the next bundle
                        }
                        break;
                    }
                }

                log.debug("Could not find the bundle or resource string for message code: " + msgCode);
                if (!this.locale.equals(StringUtils.parseLocaleString(DEFAULT_CLIENT_LOCALE))) {
                    this.locale = StringUtils.parseLocaleString(DEFAULT_CLIENT_LOCALE);
                } else {
                    loadUSDefault = false;
                }
            }
        } catch (Exception e) {
            log.debug("Could not find available resource bundle names for Locale: " + this.locale.getDisplayName() + " and message code: " + msgCode);
        }

        // If cannot translate msgCode then just return msgCode.
        return msgCode;
    }


    /**
     *
     */
    public String getMessage(String msgCode, Object[] attributes, String bundleName) {
        try {
            boolean loadUSDefault = true;
            loadBundleNames();

            while (loadUSDefault) {
                for (@SuppressWarnings("rawtypes")
                Enumeration bundleKeys = bundles.propertyNames(); bundleKeys.hasMoreElements();) {
                    String key = (String) bundleKeys.nextElement();

                    if (key.equalsIgnoreCase(bundleName)) {
                        String value = bundles.getProperty(key);
                        try {
                            ResourceBundleReader reader = new ResourceBundleReader(locale, value);
                            if (reader != null && reader.getString(msgCode, attributes) != null) {
                                return reader.getString(msgCode, attributes);
                            }
                        } catch (Exception e) {
                            // Doesn't matter. Eat it. Search in the next bundle
                        }
                        break;
                    }
                }

                log.debug("Could not find the bundle or resource string for message code: " + msgCode);
                if (!this.locale.equals(StringUtils.parseLocaleString(DEFAULT_CLIENT_LOCALE))) {
                    this.locale = StringUtils.parseLocaleString(DEFAULT_CLIENT_LOCALE);
                } else {
                    loadUSDefault = false;
                }
            }
        } catch (Exception e) {
            log.debug("Could not find available resource bundle names for Locale: " + this.locale.getDisplayName() + " and message code: " + msgCode);
        }

        // If cannot translate msgCode then just return msgCode.
        return msgCode;
    }


    @Override
    public void setTag(Object tag) {
        _tag = tag;
    }
}
