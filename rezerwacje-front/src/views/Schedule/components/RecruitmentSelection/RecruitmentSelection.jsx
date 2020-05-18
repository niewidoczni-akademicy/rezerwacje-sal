// import React from 'react';
// import clsx from 'clsx';
// import PropTypes from 'prop-types';
// import { makeStyles } from '@material-ui/styles';
// import { 
//   Card, 
//   CardContent,
//   Grid, 
//   Typography, 
//   TextField,
// } from '@material-ui/core';
// import ArrowDownwardIcon from '@material-ui/icons/ArrowDownward';

// const RecruitmentSelection = props => {
//   const { className, ...rest } = props;

//   const classes = useStyles();

//   return (
//     <Card>
//       <CardContent>
//         <Typography variant="h4" gutterBottom>
//           Recruitment
//         </Typography>
//         <Grid item xs={12}>
//           <TextField
//             fullWidth
//             helperText="Recruitment"
//             margin="dense"
//             name="recruitment"
//             onChange={handleChange}
//             required
//             select
//             SelectProps={{ native: true }}
//             value={values.recruitment}
//             variant="outlined"
//           >
//             {recruitments.map(recruitment => (
//               <option key={recruitment} value={recruitment}>{recruitment}</option>
//             ))}
//           </TextField>
//         </Grid>
//         <Grid item xs={12}>
//           <TextField
//             fullWidth
//             helperText="Cycle"
//             margin="dense"
//             name="cycle"
//             onChange={handleChange}
//             required
//             select
//             SelectProps={{ native: true }}
//             value={values.cycle}
//             variant="outlined"
//           >
//             {cycles.map(cycle => (
//               <option key={cycle} value={cycle}>{cycle}</option>
//             ))}
//           </TextField>
//         </Grid>
//       </CardContent>
//     </Card>
//   );
// };

// RecruitmentSelection.propTypes = {
//   className: PropTypes.string
// };

// export default RecruitmentSelection;
