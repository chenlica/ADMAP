/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * storage.ts maintains a set of useful static storage-related functions.
 * They are used to provide easy access to localStorage and sessionStorage.
 */

/**
 * Saves an object into the localStorage, in its the JSON format.
 * @param key - the identifier of the object
 * @param object - any type, will be JSON.stringify-ed into a string
 */
export function localSetObject<T>(key: string, object: T): void {
  localStorage.setItem(key, JSON.stringify(object));
}

/**
 * Retrieves an object from the localStorage, converted from the JSON format into its original type (provided).
 * @param key - the identifier of the object
 * @returns T - the converted object (in type<t>) from the JSON string, or null if the key is not found.
 */
export function localGetObject<T>(key: string): T | undefined {
  const data: string | null = localStorage.getItem(key);
  if (!data) {
    return undefined;
  }

  return jsonCast<T>(data);
}

/**
 * removes the object from the localStorage
 * @param {string} key - the identifier of the object
 */
export function localRemoveObject(key: string): void {
  localStorage.removeItem(key);
}

export function jsonCast<T>(data: string): T {
  return <T>JSON.parse(data);
}

/**
 * Saves an object into the sessionStorage, in its the JSON format.
 * @param key - the identifier of the object
 * @param object - any type, will be JSON.stringify-ed into a string
 */
export function sessionSetObject<T>(key: string, object: T): void {
  sessionStorage.setItem(key, JSON.stringify(object));
}

/**
 * Retrieves an object from the sessionStorage, converted from the JSON format into its original type (provided).
 * @param key - the identifier of the object
 * @returns T - the converted object (in type<t>) from the JSON string, or null if the key is not found.
 */
export function sessionGetObject<T>(key: string): T | null {
  const data: string | null = sessionStorage.getItem(key);
  if (!data) {
    return null;
  }

  return jsonCast<T>(data);
}

export function sessionRemoveObject(key: string): void {
  sessionStorage.removeItem(key);
}
