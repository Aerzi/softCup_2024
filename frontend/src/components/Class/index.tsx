// import { Form, Input, Modal, Table } from "antd";
// import React from "react";

// // 为学生返回一个课程列表
// const Class = () => {
//   return (
//     <>
//       <>
//         {/* table组件，主要用于展示当前列表中的课堂数据 */}
//         <Table
//           dataSource={classes}
//           pagination={pagination}
//           loading={loading}
//           onChange={handleTableChange}
//         >
//           <Table.Column title="课堂名称" dataIndex={"name"} key={"name"} />
//           <Table.Column
//             title="课堂描述"
//             dataIndex={"description"}
//             key={"description"}
//           />
//           <Table.Column
//             title="操作"
//             key="action"
//             render={(_, record) => (
//               <>
//                 {role === "teacher" && (
//                   <>
//                     <a
//                       onClick={() => {
//                         setEditClass(record);
//                         setIsModalOpen(true);
//                       }}
//                     >
//                       编辑
//                     </a>
//                   </>
//                 )}
//               </>
//             )}
//           />
//         </Table>
//         <Modal title="编辑课程" onOk={handleOk} onCancel={handleCancel}>
//           <Form
//             form={form}
//             initialValues={{
//               name: editClass?.name,
//               description: editClass?.description,
//             }}
//           >
//             <Form.Item
//               name="name"
//               label="课堂名称"
//               rules={[{ required: true, message: "请输入课堂名称!" }]}
//             >
//               <Input />
//             </Form.Item>
//             <Form.Item
//               name="description"
//               label="课堂描述"
//               rules={[{ required: true, message: "请输入课堂描述!" }]}
//             >
//               <Input.TextArea />
//             </Form.Item>
//           </Form>
//         </Modal>
//       </>
//     </>
//   );
// };

// export default Class;
