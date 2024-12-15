import base from './base';
import common from "./common";
import admin from './admin';
import user from './user';
import studio from './studio';
import project from './project';
import resource from './resource';
import dataSource from "./dataSource";
import oam from "./oam";
import stdata from "./stdata";
import editor from "./editor";
import metadata from "./metadata";
import workspace from "./workspace";

export default {
  ...base,
  ...common,
  ...admin,
  ...user,
  ...studio,
  ...project,
  ...resource,
  ...dataSource,
  ...oam,
  ...stdata,
  ...editor,
  ...metadata,
  ...workspace,
};
