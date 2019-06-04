/*
 * Copyright 2019 Rundeck, Inc. (http://rundeck.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rundeck.app.menu

import grails.web.mapping.LinkGenerator
import org.rundeck.app.gui.MenuItem
import org.springframework.beans.factory.annotation.Autowired


class WebhooksMenuItem implements MenuItem {

    String title = "Webhooks"
    String titleCode = "Webhooks.title"
    MenuItem.MenuType type = MenuType.SYSTEM_CONFIG

    @Autowired
    LinkGenerator grailsLinkGenerator

    @Override
    String getHref() {
        return grailsLinkGenerator.link(uri:"/webhooks")
    }
}
