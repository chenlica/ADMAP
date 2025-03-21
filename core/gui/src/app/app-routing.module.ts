import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { environment } from "../environments/environment";
import { ProfileComponent } from "./profile/component/profile.component";
import { DashboardComponent } from "./dashboard/component/dashboard.component";
import { UserQuotaComponent } from "./dashboard/component/user/user-quota/user-quota.component";
import { UserProjectSectionComponent } from "./dashboard/component/user/user-project/user-project-section/user-project-section.component";
import { UserProjectComponent } from "./dashboard/component/user/user-project/user-project.component";
import { WorkspaceComponent } from "./workspace/component/workspace.component";
import { AboutComponent } from "./hub/component/about/about.component";
import { AuthGuardService } from "./common/service/user/auth-guard.service";
import { AdminUserComponent } from "./dashboard/component/admin/user/admin-user.component";
import { AdminExecutionComponent } from "./dashboard/component/admin/execution/admin-execution.component";
import { AdminGuardService } from "./dashboard/service/admin/guard/admin-guard.service";
import { SearchComponent } from "./dashboard/component/user/search/search.component";
import { FlarumComponent } from "./dashboard/component/user/flarum/flarum.component";
import { AdminGmailComponent } from "./dashboard/component/admin/gmail/admin-gmail.component";
import { DatasetDetailComponent } from "./dashboard/component/user/user-dataset/user-dataset-explorer/dataset-detail.component";
import { UserDatasetComponent } from "./dashboard/component/user/user-dataset/user-dataset.component";
import { LandingPageComponent } from "./hub/component/landing-page/landing-page.component";
import { DASHBOARD_HOME } from "./app-routing.constant";
import { HubSearchResultComponent } from "./hub/component/hub-search-result/hub-search-result.component";
import { FileDirectoryComponent } from "./dashboard/component/user/file-directory/file-directory.component";
import { MetadataDirectoryComponent } from "./dashboard/component/user/metadata-directory/metadata-directory.component";
import { MetadataDetailComponent } from "./dashboard/component/user/metadata-directory/metadata-dataset-explorer/metadata-detail.component";

const routes: Routes = [];

if (environment.userSystemEnabled) {
  routes.push({
    path: "dashboard",
    component: DashboardComponent,
    children: [
      {
        path: "home",
        component: LandingPageComponent,
      },
      {
        path: "about",
        component: AboutComponent,
      },
      {
        path: "hub",
        children: [
          {
            path: "dataset",
            children: [
              {
                path: "result",
                component: HubSearchResultComponent,
              },
              {
                path: "result/detail/:did",
                component: DatasetDetailComponent,
              },
            ],
          },
        ],
      },
      {
        path: "user",
        canActivate: [AuthGuardService],
        children: [
          {
            path: "project",
            component: UserProjectComponent,
          },
          {
            path: "project/:pid",
            component: UserProjectSectionComponent,
          },
          {
            path: "dataset",
            component: UserDatasetComponent,
          },
          {
            path: "directory",
            component: FileDirectoryComponent,
          },
          {
            path: "dataset/:did",
            component: DatasetDetailComponent,
          },
          {
            path: "dataset/create",
            component: DatasetDetailComponent,
          },
          {
            path: "directory",
            component: FileDirectoryComponent,
          },
          {
            path: "metadata",
            component: MetadataDirectoryComponent,
          },
          {
            path: "metadata/create",
            component: MetadataDetailComponent,
          },
          {
            path: "quota",
            component: UserQuotaComponent,
          },
          {
            path: "discussion",
            component: FlarumComponent,
          },
          {
            path: "profile",
            component: ProfileComponent,
          },
        ],
      },
      {
        path: "admin",
        canActivate: [AdminGuardService],
        children: [
          {
            path: "user",
            component: AdminUserComponent,
          },
          {
            path: "gmail",
            component: AdminGmailComponent,
          },
          {
            path: "execution",
            component: AdminExecutionComponent,
          },
        ],
      },
      {
        path: "search",
        component: SearchComponent,
      },
    ],
  });

  routes.push({
    path: "",
    redirectTo: DASHBOARD_HOME,
    pathMatch: "full",
  });
} else {
  routes.push({
    path: "",
    component: WorkspaceComponent,
  });
}

// redirect all other paths to dashboard home.
routes.push({
  path: "**",
  redirectTo: DASHBOARD_HOME,
});

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
