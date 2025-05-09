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

import { Component, ComponentRef, ChangeDetectorRef, OnInit, OnDestroy } from "@angular/core";
import { UntilDestroy, untilDestroyed } from "@ngneat/until-destroy";
import { FieldType, FieldTypeConfig } from "@ngx-formly/core";
import { CodeEditorComponent } from "../code-editor-dialog/code-editor.component";
import { CoeditorPresenceService } from "../../service/workflow-graph/model/coeditor-presence.service";
import { CodeEditorService } from "../../service/code-editor/code-editor.service";
import { WorkflowActionService } from "../../service/workflow-graph/model/workflow-action.service";

/**
 * CodeareaCustomTemplateComponent is the custom template for 'codearea' type of formly field.
 *
 * When the formly field type is 'codearea', it overrides the default one line string input template
 * with this component.
 */
@UntilDestroy()
@Component({
  selector: "texera-codearea-custom-template",
  templateUrl: "codearea-custom-template.component.html",
  styleUrls: ["codearea-custom-template.component.scss"],
})
export class CodeareaCustomTemplateComponent extends FieldType<FieldTypeConfig> implements OnInit, OnDestroy {
  componentRef: ComponentRef<CodeEditorComponent> | undefined;
  public isEditorOpen: boolean = false;
  private operatorID: string = "";

  constructor(
    private coeditorPresenceService: CoeditorPresenceService,
    private codeEditorService: CodeEditorService,
    private changeDetectorRef: ChangeDetectorRef,
    private workflowActionService: WorkflowActionService
  ) {
    super();
    this.coeditorPresenceService
      .getCoeditorOpenedCodeEditorSubject()
      .pipe(untilDestroyed(this))
      .subscribe(_ => this.openEditor());
    this.coeditorPresenceService
      .getCoeditorClosedCodeEditorSubject()
      .pipe(untilDestroyed(this))
      .subscribe(_ => this.componentRef?.destroy());
  }

  ngOnInit() {
    this.operatorID = this.getOperatorID();
    this.codeEditorService
      .getEditorState(this.operatorID)
      .pipe(untilDestroyed(this))
      .subscribe(isOpen => {
        this.isEditorOpen = isOpen;
        this.changeDetectorRef.detectChanges();
      });
  }

  openEditor(): void {
    this.componentRef = this.codeEditorService.vc.createComponent(CodeEditorComponent);
    this.componentRef.instance.componentRef = this.componentRef;
    this.componentRef.instance.formControl = this.field.formControl;
    this.isEditorOpen = true;

    this.codeEditorService.setEditorState(this.operatorID, true);

    this.componentRef.onDestroy(() => {
      this.isEditorOpen = false;
      this.codeEditorService.setEditorState(this.operatorID, false);
      this.changeDetectorRef.detectChanges();
    });
  }

  ngOnDestroy() {
    this.codeEditorService.setEditorState(this.operatorID, this.isEditorOpen);
  }

  private getOperatorID(): string {
    return this.workflowActionService.getJointGraphWrapper().getCurrentHighlightedOperatorIDs()[0];
  }
}
