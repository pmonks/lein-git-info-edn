;********************************************************************************
;* Copyright © 2015 Justin Glenn Smith
;*
;* This program and the accompanying materials are made available under the
;* terms of the Eclipse Public License 1.0 which is available at
;* http://www.eclipse.org/legal/epl-1.0.
;*
;* SPDX-License-Identifier: EPL-1.0
;*
;* Contributors:
;*   Justin Glenn Smith - initial implementation
;*   Odin Hole Standal  - write proper EDN
;*   Peter Monks        - add deps.edn plugin and restructure code
;********************************************************************************/

(ns noisesmith.git-info
  (:require [clojure.java.shell :as sh]
            [clojure.string :as string]
            [clojure.java.io :as io]
            [clojure.pprint :as pprint])
  (:import (java.time Instant)))

(def date-emitter
  "needed in order to support #inst output in Clojure 1.10.0"
  (reify Object
    (toString [this]
      (str "#inst \"" (Instant/now) "\""))))

(defmethod print-method (class date-emitter) [x ^java.io.Writer w]
  (.write w (str x)))

(defn generate-info-str
  []
  (with-out-str
   (pprint/pprint {:hash (string/trim (:out (sh/sh "git" "rev-parse" "HEAD")))
                   :status (string/trim (:out (sh/sh "git" "status")))
                   :date date-emitter})))

(defn git-info-edn
  "writes git info to the specified EDN file"
  [output-file]
  (io/make-parents output-file)
  (spit output-file (generate-info-str)))

(defn -main
  "entry point for the tools.deps plugin"
  [& source-paths]
  (git-info-edn "resources/deploy-info.edn")
  (System/exit 0))
