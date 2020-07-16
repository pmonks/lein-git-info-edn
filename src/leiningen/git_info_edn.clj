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
;*   Peter Monks        - add deps.edn plugin and restructure code
;********************************************************************************/

(ns leiningen.git-info-edn
  (:require [noisesmith.git-info :as gi]))

(defn git-info-edn
  "Entry point for the leiningen plugin"
  [& _]
  (gi/git-info-edn "resources/deploy-info.edn")
  true)
