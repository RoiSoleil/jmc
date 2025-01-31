/*
 * Copyright (c) 2018, 2025, Oracle and/or its affiliates. All rights reserved.
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * The contents of this file are subject to the terms of either the Universal Permissive License
 * v 1.0 as shown at https://oss.oracle.com/licenses/upl
 *
 * or the following license:
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.openjdk.jmc.ide.launch.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.openjdk.jmc.flightrecorder.configuration.spi.IConfigurationStorageDelegate;
import org.openjdk.jmc.ide.launch.Messages;

/**
 * Storage delegate for templates that reside inside a JRE on the file system.
 */
public class JreFileStorageDelegate implements IConfigurationStorageDelegate {
	private final String locationInfo = Messages.VOLATILE_CONFIGURATION_IN_JRE;
	private final String name;
	private File jfcFile;

	public JreFileStorageDelegate(String name, File jfcFile) {
		this.name = name;
		this.jfcFile = jfcFile;
	}

	@Override
	public InputStream getContents() {
		try {
			return jfcFile.exists() ? new FileInputStream(jfcFile) : emptyInputStream();
		} catch (FileNotFoundException e) {
			// FIXME: What should we do in this case, should we throw or return something?
			return emptyInputStream();
		}
	}

	private ByteArrayInputStream emptyInputStream() {
		return new ByteArrayInputStream(new byte[0]);
	}

	@Override
	public boolean isSaveable() {
		return false;
	}

	@Override
	public boolean save(String fileContent) {
		return false;
	}

	@Override
	public boolean isDeletable() {
		return false;
	}

	@Override
	public boolean delete() {
		return false;
	}

	@Override
	public String getLocationInfo() {
		return locationInfo;
	}

	@Override
	public String getLocationPath() {
		return name;
	}
}
