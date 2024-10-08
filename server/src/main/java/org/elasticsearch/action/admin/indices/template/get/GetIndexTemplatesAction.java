/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the "Elastic License
 * 2.0", the "GNU Affero General Public License v3.0 only", and the "Server Side
 * Public License v 1"; you may not use this file except in compliance with, at
 * your election, the "Elastic License 2.0", the "GNU Affero General Public
 * License v3.0 only", or the "Server Side Public License, v 1".
 */
package org.elasticsearch.action.admin.indices.template.get;

import org.elasticsearch.action.ActionType;

public class GetIndexTemplatesAction extends ActionType<GetIndexTemplatesResponse> {

    public static final GetIndexTemplatesAction INSTANCE = new GetIndexTemplatesAction();
    public static final String NAME = "indices:admin/template/get";

    protected GetIndexTemplatesAction() {
        super(NAME);
    }
}
