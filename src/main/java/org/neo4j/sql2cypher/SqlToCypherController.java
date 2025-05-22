/*
 * Copyright (c) 2023-2025 "Neo4j,"
 * Neo4j Sweden AB [https://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neo4j.sql2cypher;

import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.neo4j.jdbc.translator.impl.SqlToCypherTranslatorFactory;
import org.neo4j.jdbc.translator.spi.Translator;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SqlToCypherController {

	private final Translator translator = new SqlToCypherTranslatorFactory()
		.create(Map.of("s2c.prettyPrint", "true", "s2c.enableCache", "true"));

	@PostMapping(value = "/translate", consumes = { MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<String> translate(@Valid @RequestBody @NotNull Request request) {
		return translate(request.sql());
	}

	@PostMapping(value = "/translate", consumes = { MediaType.TEXT_PLAIN_VALUE })
	ResponseEntity<String> translate(@RequestBody @NotBlank String sql) {
		try {
			return ResponseEntity.ok(this.translator.translate(sql) + "\n");
		}
		catch (Exception ex) {
			return ResponseEntity.internalServerError()
				.body("Could not translate %s: %s".formatted(sql, ex.getMessage()));
		}
	}

	public record Request(@NotBlank String sql) {
	}

}
