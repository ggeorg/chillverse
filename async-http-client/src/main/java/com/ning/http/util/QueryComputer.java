/*
 * Copyright (c) 2014 AsyncHttpClient Project. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package com.ning.http.util;

import static com.ning.http.util.MiscUtils.isNonEmpty;
import static com.ning.http.util.AsyncHttpProviderUtils.encodeAndAppendQueryParam;
import static com.ning.http.util.UTF8UrlEncoder.encodeAndAppendQuery;
import com.ning.http.client.Param;

import java.util.List;

public enum QueryComputer {

    URL_ENCODING_ENABLED_QUERY_COMPUTER {

        private final void encodeAndAppendQueryParams(final StringBuilder sb, final List<Param> queryParams) {
            for (Param param : queryParams)
                encodeAndAppendQueryParam(sb, param.getName(), param.getValue());
        }
        
        protected final String withQueryWithParams(final String query, final List<Param> queryParams) {
            // concatenate encoded query + encoded query params
            StringBuilder sb = StringUtils.stringBuilder();
            encodeAndAppendQuery(sb, query);
            sb.append('&');
            encodeAndAppendQueryParams(sb, queryParams);
            sb.setLength(sb.length() - 1);
            return sb.toString();
        }

        protected final String withQueryWithoutParams(final String query) {
            // encode query
            StringBuilder sb = StringUtils.stringBuilder();
            encodeAndAppendQuery(sb, query);
            return sb.toString();
        }

        protected final String withoutQueryWithParams(final List<Param> queryParams) {
            // concatenate encoded query params
            StringBuilder sb = StringUtils.stringBuilder();
            encodeAndAppendQueryParams(sb, queryParams);
            sb.setLength(sb.length() - 1);
            return sb.toString();
        }
    }, //

    URL_ENCODING_DISABLED_QUERY_COMPUTER {

        private final void appendRawQueryParam(StringBuilder sb, String name, String value) {
            sb.append(name);
            if (value != null)
                sb.append('=').append(value);
            sb.append('&');
        }
        
        private final void appendRawQueryParams(final StringBuilder sb, final List<Param> queryParams) {
            for (Param param : queryParams)
                appendRawQueryParam(sb, param.getName(), param.getValue());
        }
        
        protected final String withQueryWithParams(final String query, final List<Param> queryParams) {
            // concatenate raw query + raw query params
            StringBuilder sb = StringUtils.stringBuilder();
            sb.append(query);
            appendRawQueryParams(sb, queryParams);
            sb.setLength(sb.length() - 1);
            return sb.toString();
        }

        protected final String withQueryWithoutParams(final String query) {
            // return raw query as is
            return query;
        }

        protected final String withoutQueryWithParams(final List<Param> queryParams) {
            // concatenate raw queryParams
            StringBuilder sb = StringUtils.stringBuilder();
            appendRawQueryParams(sb, queryParams);
            sb.setLength(sb.length() - 1);
            return sb.toString();
        }
    };

    public static QueryComputer queryComputer(boolean disableUrlEncoding) {
        return disableUrlEncoding ? URL_ENCODING_DISABLED_QUERY_COMPUTER : URL_ENCODING_ENABLED_QUERY_COMPUTER;
    }

    protected abstract String withQueryWithParams(final String query, final List<Param> queryParams);

    protected abstract String withQueryWithoutParams(final String query);

    protected abstract String withoutQueryWithParams(final List<Param> queryParams);

    private final String withQuery(final String query, final List<Param> queryParams) {
        return isNonEmpty(queryParams) ? withQueryWithParams(query, queryParams) : withQueryWithoutParams(query);
    }

    private final String withoutQuery(final List<Param> queryParams) {
        return isNonEmpty(queryParams) ? withoutQueryWithParams(queryParams) : null;
    }

    public final String computeFullQueryString(final String query, final List<Param> queryParams) {
        return isNonEmpty(query) ? withQuery(query, queryParams) : withoutQuery(queryParams);
    }
}
