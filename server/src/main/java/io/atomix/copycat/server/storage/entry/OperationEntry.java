/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.atomix.copycat.server.storage.entry;

import io.atomix.copycat.Operation;

/**
 * Stores a state machine operation.
 * <p>
 * Each state machine operation is stored with a client-provided {@link #sequence() sequence number}.
 * The sequence number is used by state machines to apply client operations in the order in which they
 * were submitted by the client (FIFO order). Additionally, each operation is written with the leader's
 * {@link #timestamp() timestamp} at the time the entry was logged. This gives state machines an
 * approximation of time with which to react to the application of operations to the state machine.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 */
public abstract class OperationEntry<T extends OperationEntry<T>> extends SessionEntry<T> {
  protected final long sequence;

  protected OperationEntry(long timestamp, long session, long sequence) {
    super(timestamp, session);
    this.sequence = sequence;
  }

  /**
   * Returns the entry operation.
   *
   * @return The entry operation.
   */
  public abstract Operation operation();

  /**
   * Returns the operation sequence number.
   *
   * @return The operation sequence number.
   */
  public long sequence() {
    return sequence;
  }

  /**
   * Operation entry serializer.
   */
  public static abstract class Serializer<T extends OperationEntry> extends SessionEntry.Serializer<T> {
  }
}
