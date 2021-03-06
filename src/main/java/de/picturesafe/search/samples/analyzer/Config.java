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

package de.picturesafe.search.samples.analyzer;

import de.picturesafe.search.elasticsearch.config.ElasticsearchType;
import de.picturesafe.search.elasticsearch.config.FieldConfiguration;
import de.picturesafe.search.elasticsearch.config.impl.StandardFieldConfiguration;
import de.picturesafe.search.spring.configuration.DefaultClientConfiguration;
import de.picturesafe.search.spring.configuration.DefaultQueryConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Collections;
import java.util.List;

import static de.picturesafe.search.samples.analyzer.CustomIndexConfiguration.CUSTOM_ANALYZER_NAME;

@Configuration
@ComponentScan(basePackages = {"de.picturesafe.search.elasticsearch"})
@Import({DefaultClientConfiguration.class, CustomIndexConfiguration.class, DefaultQueryConfiguration.class})
public class Config {

    @Bean
    List<FieldConfiguration> fieldConfigurations() {
        return Collections.singletonList(
                StandardFieldConfiguration.builder("filename", ElasticsearchType.TEXT).analyzer(CUSTOM_ANALYZER_NAME).build()
        );
    }
}
