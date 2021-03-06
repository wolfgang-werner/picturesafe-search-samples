/*
 * Copyright 2020 picturesafe media/data/bank GmbH
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

package de.picturesafe.search.samples.expressions;

import de.picturesafe.search.elasticsearch.DataChangeProcessingMode;
import de.picturesafe.search.elasticsearch.SingleIndexElasticsearchService;
import de.picturesafe.search.elasticsearch.model.DocumentBuilder;
import de.picturesafe.search.elasticsearch.model.SearchResult;
import de.picturesafe.search.expression.Expression;
import de.picturesafe.search.expression.RangeValueExpression;
import de.picturesafe.search.parameter.SearchParameter;
import de.picturesafe.search.samples.PicturesafeSearchSample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ComponentScan
public class SearchRangeValueExpression implements PicturesafeSearchSample {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchRangeValueExpression.class);

    @Autowired
    private SingleIndexElasticsearchService singleIndexElasticsearchService;

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SearchRangeValueExpression.class)) {
            final SearchRangeValueExpression searchRangeValueExpression = ctx.getBean(SearchRangeValueExpression.class);
            searchRangeValueExpression.run();
        }
    }

    @Override
    public void run() {
        try {
            singleIndexElasticsearchService.createIndexWithAlias();

            singleIndexElasticsearchService.addToIndex(DataChangeProcessingMode.BLOCKING, Arrays.asList(
                    DocumentBuilder.id(1).put("quantity", 0).build(),
                    DocumentBuilder.id(2).put("quantity", 100).build(),
                    DocumentBuilder.id(3).put("quantity", 180).build(),
                    DocumentBuilder.id(4).put("quantity", 1000).build(),
                    DocumentBuilder.id(5).put("quantity", 2000).build()
            ));

            final Expression expression = new RangeValueExpression("quantity", 50, 1000);
            final SearchResult searchResult = singleIndexElasticsearchService.search(expression, SearchParameter.DEFAULT);
            LOGGER.info(searchResult.toString());
        } finally {
            singleIndexElasticsearchService.deleteIndexWithAlias();
        }
    }
}
